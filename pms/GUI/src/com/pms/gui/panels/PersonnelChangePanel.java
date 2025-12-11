package com.pms.gui.panels;


import com.pms.model.Employee;
import com.pms.model.PersonnelChange;
import com.pms.service.EmployeeService;
import com.pms.service.DepartmentService;
import com.pms.service.PersonnelChangeService;
import com.pms.gui.dialogs.PersonnelChangeDetailDialog;
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
        JButton refreshBtn = new JButton("刷新");

        refreshBtn.addActionListener(e -> {
            currentPage = 1;
            loadChangeData();
        });

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

        // 添加双击事件,双击显示人事变动详细信息
        // 优化双击事件处理，确保稳定触发
        changeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                // 1. 验证事件源和点击条件：必须是左键双击且点击在表格内容区域
                if (evt.getButton() != MouseEvent.BUTTON1 || evt.getClickCount() != 2) {
                    return;
                }

                // 2. 确定点击位置是否在有效单元格上
                int row = changeTable.rowAtPoint(evt.getPoint());
                int col = changeTable.columnAtPoint(evt.getPoint());
                if (row == -1 || col == -1) { // 点击在表格空白区域
                    return;
                }

                // 3. 确保行被选中（处理表格选择模式可能的影响）
                changeTable.setRowSelectionInterval(row, row);

                // 4. 转换为模型索引（处理排序/过滤后的索引映射）
                int modelRow = changeTable.convertRowIndexToModel(row);

                try {
                    // 5. 安全获取变动记录ID（避免类型转换异常）
                    Object idObj = changeTable.getModel().getValueAt(modelRow, 0);
                    if (!(idObj instanceof Integer)) {
                        JOptionPane.showMessageDialog(PersonnelChangePanel.this,
                                "无效的记录ID格式", "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int changeId = (Integer) idObj;

                    // 6. 显示变动记录详细信息
                    showChangeDetailDialog(changeId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(PersonnelChangePanel.this,
                            "显示详细信息失败: " + ex.getMessage(),
                            "错误", JOptionPane.ERROR_MESSAGE);
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

    // 从数据库获取变动记录
    private List<PersonnelChange> getChangesFromDB() {
        List<PersonnelChange> changes = new ArrayList<>();
        // 排序字段映射：适配personnel表字段
        String[] sortFields = {"p.id", "p.person_id", "p.person_name", "pc.description", "p.description", "p.change_time"};
        String sortField = sortFields[sortColumn];
        String order = ascending ? "ASC" : "DESC";
        int offset = (currentPage - 1) * pageSize;

        // 查询personnel表获取变动记录
        String countSql = "SELECT COUNT(*) FROM personnel p";

        String dataSql = String.format(
                "SELECT p.id, p.person_id, p.person_name, pc.description, p.description, p.change_time " +
                        "FROM personnel p " +
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
                change.setChangeType(rs.getString(4));       // 变动类型
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
        String sql = "SELECT p.id, p.person_id, p.person_name, pc.description, p.description, p.change_time " +
                "FROM personnel p " +
                "LEFT JOIN personnel_change pc ON p.`change` = pc.code " +
                "WHERE p.person_name LIKE ? OR p.person_id = ? OR pc.description LIKE ? OR p.description LIKE ?";

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
            pstmt.setString(4, likeKeyword); // 搜索变动描述

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

    // 添加变动记录功能已删除，统一使用员工管理界面的添加功能


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

    // 显示人事变动详细信息对话框
    private void showChangeDetailDialog(int changeId) {
        // 调用服务层获取详细信息
        PersonnelChangeService service = new PersonnelChangeService();
        PersonnelChange change = service.getChangeById(changeId);

        if (change == null) {
            JOptionPane.showMessageDialog(this, "未找到该记录的详细信息", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // 创建并显示详细信息对话框（使用与员工详细信息界面相同的格式）
        PersonnelChangeDetailDialog dialog = new PersonnelChangeDetailDialog(
                SwingUtil.getParentFrame(this),
                "人事变动详细信息",
                change
        );
        dialog.setVisible(true);
    }
}