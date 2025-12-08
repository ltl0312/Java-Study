package com.pms.gui.panels;

import com.pms.gui.dialogs.PersonnelChangeDialog;
import com.pms.model.Employee;
import com.pms.model.PersonnelChange;
import com.pms.service.EmployeeService;
import com.pms.service.PersonnelChangeService;
import com.pms.utils.DBConnection;
import com.pms.utils.SwingUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonnelChangePanel extends JPanel {
    private JTable changeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    // 分页相关
    private int currentPage = 1;
    private final int pageSize = 15;
    private int totalRecords = 0;
    private JLabel pageInfoLabel;
    private JButton prevBtn;
    private JButton nextBtn;

    // 排序相关
    private int sortColumn = 0;
    private boolean ascending = true;

    public PersonnelChangePanel() {
        initUI();
        loadChangeData();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // 顶部工具栏
        JPanel toolPanel = new JPanel(new BorderLayout());
        toolPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // 搜索面板
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("搜索员工:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchBtn = new JButton("搜索");
        searchBtn.addActionListener(this::searchChanges);
        searchPanel.add(searchBtn);
        toolPanel.add(searchPanel, BorderLayout.WEST);

        // 操作按钮
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addBtn = new JButton("添加变动记录");
        JButton deleteBtn = new JButton("删除记录");
        JButton refreshBtn = new JButton("刷新");

        addBtn.addActionListener(this::addChangeRecord);
        deleteBtn.addActionListener(this::deleteChangeRecord);
        refreshBtn.addActionListener(e -> {
            currentPage = 1;
            loadChangeData();
        });

        btnPanel.add(addBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);
        toolPanel.add(btnPanel, BorderLayout.EAST);

        add(toolPanel, BorderLayout.NORTH);

        // 表格设置
        String[] columnNames = {"记录ID", "员工ID", "员工姓名", "变动类型", "变动描述", "变动时间"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        changeTable = new JTable(tableModel);
        changeTable.setRowHeight(35);
        changeTable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        changeTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 15));

        // 列宽设置
        changeTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        changeTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        changeTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        changeTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        changeTable.getColumnModel().getColumn(4).setPreferredWidth(250);
        changeTable.getColumnModel().getColumn(5).setPreferredWidth(150);

        // 表头排序（仅允许ID、员工ID排序，先屏蔽时间排序避免字段错误）
        changeTable.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = changeTable.columnAtPoint(e.getPoint());
                // 暂时只允许记录ID(0)、员工ID(1)排序，避免时间字段错误
                if (column == 0 || column == 1) {
                    if (column == sortColumn) {
                        ascending = !ascending;
                    } else {
                        sortColumn = column;
                        ascending = true;
                    }
                    currentPage = 1;
                    loadChangeData();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(changeTable);
        add(scrollPane, BorderLayout.CENTER);

        // 分页控件
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paginationPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        prevBtn = new JButton("上一页");
        prevBtn.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                loadChangeData();
            }
        });

        pageInfoLabel = new JLabel("第 1 页，共 0 页");

        nextBtn = new JButton("下一页");
        nextBtn.addActionListener(e -> {
            int totalPages = (totalRecords + pageSize - 1) / pageSize;
            if (currentPage < totalPages) {
                currentPage++;
                loadChangeData();
            }
        });

        paginationPanel.add(prevBtn);
        paginationPanel.add(pageInfoLabel);
        paginationPanel.add(nextBtn);
        add(paginationPanel, BorderLayout.SOUTH);
    }

    // 加载变动记录数据
    private void loadChangeData() {
        tableModel.setRowCount(0);
        List<PersonnelChange> changes = getChangesFromDB();

        for (PersonnelChange change : changes) {
            tableModel.addRow(new Object[]{
                    change.getId(),
                    change.getEmployeeId(),
                    change.getEmployeeName(),
                    change.getChangeType(),
                    change.getDescription(),
                    change.getChangeTime()
            });
        }

        updatePageInfo();
    }

    // 从数据库获取变动记录（核心修正：适配通用表结构，解决字段不存在问题）
    // 从数据库获取变动记录（修正版）
    private List<PersonnelChange> getChangesFromDB() {
        List<PersonnelChange> changes = new ArrayList<>();
        // 排序字段映射：适配数据库字段
        String[] sortFields = {"p.id", "p.person", "pe.name", "pc.description", "p.description", "p.change_time"};
        String sortField = sortFields[sortColumn];
        String order = ascending ? "ASC" : "DESC";
        int offset = (currentPage - 1) * pageSize;

        // 修正：正确关联表并获取所有字段
        String countSql = "SELECT COUNT(*) FROM personnel p " +
                "LEFT JOIN person pe ON p.person_id = pe.id " +
                "LEFT JOIN personnel_change pc ON p.`change` = pc.code";

        String dataSql = String.format(
                "SELECT p.id, p.person_id, pe.name, pc.description, p.description, p.change_time " +
                        "FROM personnel p " +
                        "LEFT JOIN person pe ON p.person_id = pe.id " +
                        "LEFT JOIN personnel_change pc ON p.`change` = pc.code " +
                        "ORDER BY %s %s LIMIT %d, %d",
                sortField, order, offset, pageSize
        );

        try (Connection conn = DBConnection.getConnection()) {
            // 获取总记录数
            PreparedStatement countStmt = conn.prepareStatement(countSql);
            ResultSet countRs = countStmt.executeQuery();
            if (countRs.next()) {
                totalRecords = countRs.getInt(1);
            }

            // 获取分页数据
            PreparedStatement dataStmt = conn.prepareStatement(dataSql);
            ResultSet rs = dataStmt.executeQuery();
            while (rs.next()) {
                PersonnelChange change = new PersonnelChange();
                change.setId(rs.getInt(1));                  // 记录ID
                change.setEmployeeId(rs.getInt(2));          // 员工ID
                change.setEmployeeName(rs.getString(3));     // 员工姓名
                change.setChangeType(rs.getString(4));       // 变动类型（从personnel_change表获取）
                change.setDescription(rs.getString(5));      // 变动描述
                change.setChangeTime(rs.getTimestamp(6));    // 变动时间（包含时分）
                changes.add(change);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载变动记录失败: " + e.getMessage());
        }
        return changes;
    }

    // 搜索变动记录
    private void searchChanges(ActionEvent e) {
        String keyword = searchField.getText().trim();
        tableModel.setRowCount(0);
        String sql = "SELECT p.id, p.person, pe.name, pc.description, p.description, p.change_time " +
                "FROM personnel p " +
                "LEFT JOIN person pe ON p.person = pe.id " +
                "LEFT JOIN personnel_change pc ON p.`change` = pc.code " +
                "WHERE pe.name LIKE ? OR p.person = ? OR pc.description LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String likeKeyword = "%" + keyword + "%";
            pstmt.setString(1, likeKeyword); // 搜索员工姓名
            try {
                pstmt.setInt(2, Integer.parseInt(keyword)); // 搜索员工ID
            } catch (NumberFormatException ex) {
                pstmt.setInt(2, -1); // 无效ID，不匹配
            }
            pstmt.setString(3, likeKeyword); // 搜索变动类型

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getTimestamp(6)
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "搜索失败: " + ex.getMessage());
        }
    }

    // 添加变动记录
    private void addChangeRecord(ActionEvent e) {
        // 1. 打开对话框获取用户输入的变动信息（假设已有对话框返回变动对象）
        PersonnelChange change = showAddChangeDialog(); // 自定义方法，返回用户输入的变动信息
        if (change == null) return;
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // 开启事务（确保变动记录和员工更新同时成功/失败）
            // 2. 添加人事变动记录
            PersonnelChangeService changeService = new PersonnelChangeService();
            System.out.println(changeService.getClass().getDeclaredMethods()); // 打印所有方法
            System.out.println(changeService.getClass().getDeclaredFields());  // 打印所有属性
            boolean isChangeAdded = changeService.addChange(change);
            if (!isChangeAdded) {
                throw new SQLException("添加变动记录失败");
            }

            // 3. 根据变动类型更新员工信息
            EmployeeService empService = new EmployeeService();
            Employee employee = empService.getEmployeeById(change.getEmployeeId()); // 需实现该方法
            if (employee == null) {
                throw new SQLException("未找到ID为" + change.getEmployeeId() + "的员工");
            }

            switch (change.getChangeType()) {
                case "辞退":
                    // 辞退→更新员工状态为“非在职”
                    employee.setState('f');
                    empService.updateEmployee(employee);
                    break;
                case "职务变动":
                    // 职务变动→更新员工职位代码
                    employee.setJobCode(change.getNewJobCode());
                    empService.updateEmployee(employee);
                    break;
                case "部门变动":
                    // 部门变动→更新员工部门ID
                    employee.setDepartmentId(change.getNewDepartmentId());
                    empService.updateEmployee(employee);
                    break;
                // 其他变动类型（如“新员工加入”一般通过员工添加界面触发，此处可忽略）
            }

            conn.commit(); // 事务提交
            JOptionPane.showMessageDialog(this, "变动记录添加成功，员工信息已同步更新");
            loadChangeData(); // 刷新变动列表
        } catch (SQLException ex) {
            if (conn != null) try { conn.rollback(); } catch (SQLException e1) {e1.printStackTrace();} // 事务回滚
            ex.printStackTrace();

            JOptionPane.showMessageDialog(this, "操作失败：" + ex.getMessage());

        } finally {
            if (conn != null) try { conn.close(); } catch (SQLException e1) {e1.printStackTrace();}
        }
    }

    // 辅助方法：打开对话框获取变动信息（示例）
    private PersonnelChange showAddChangeDialog() {
        // 创建并显示人事变动对话框
        PersonnelChangeDialog dialog = new PersonnelChangeDialog(
                SwingUtil.getParentFrame(this),
                "添加人事变动记录"
        );

        // 显示模态对话框
        dialog.setVisible(true);

        // 如果用户点击了确认按钮，返回输入的数据
        if (dialog.isConfirmed()) {
            return dialog.getPersonnelChange();
        }

        // 用户取消操作，返回null
        return null;
    }


    // 删除变动记录
    private void deleteChangeRecord(ActionEvent e) {
        int selectedRow = changeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要删除的记录", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // 获取选中记录的ID
        int modelRow = changeTable.convertRowIndexToModel(selectedRow);
        int changeId = (int) tableModel.getValueAt(modelRow, 0);

        // 确认删除
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "确定要删除ID为" + changeId + "的记录吗？",
                "确认删除",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // 调用服务层删除
        PersonnelChangeService service = new PersonnelChangeService();
        if (service.deleteChange(changeId)) {
            JOptionPane.showMessageDialog(this, "删除成功");
            loadChangeData(); // 刷新数据
        } else {
            JOptionPane.showMessageDialog(this, "删除失败", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 更新分页信息
    private void updatePageInfo() {
        int totalPages = (totalRecords + pageSize - 1) / pageSize;
        pageInfoLabel.setText(String.format("第 %d 页，共 %d 页", currentPage, totalPages));
        prevBtn.setEnabled(currentPage > 1);
        nextBtn.setEnabled(currentPage < totalPages);
    }

}