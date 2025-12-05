package com.pms.gui.panels;

import com.pms.gui.dialogs.DepartmentDetailDialog;
import com.pms.gui.dialogs.DepartmentDialog;
import com.pms.model.Department;
import com.pms.service.DepartmentService;
import com.pms.utils.DBConnection;
import com.pms.utils.SwingUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DepartmentPanel extends JPanel {
    private JTable departmentTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    // 分页相关组件
    private int currentPage = 1;
    private final int pageSize = 15;
    private int totalRecords = 0;
    private JLabel pageInfoLabel;
    private JButton prevBtn;
    private JButton nextBtn;

    // 排序相关
    private int sortColumn = 0;
    private boolean ascending = true;

    public DepartmentPanel() {
        initUI();
        loadDepartmentData();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // 顶部工具栏
        JPanel toolPanel = new JPanel(new BorderLayout());
        toolPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // 搜索面板
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("搜索部门:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchBtn = new JButton("搜索");
        searchBtn.addActionListener(this::searchDepartments);
        searchPanel.add(searchBtn);
        toolPanel.add(searchPanel, BorderLayout.WEST);

        // 操作按钮
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addBtn = new JButton("添加");
        JButton editBtn = new JButton("编辑");
        JButton deleteBtn = new JButton("删除");
        JButton refreshBtn = new JButton("刷新");

        addBtn.addActionListener(this::addDepartment);
        editBtn.addActionListener(this::editDepartment);
        deleteBtn.addActionListener(this::deleteDepartment);
        refreshBtn.addActionListener(e -> {
            currentPage = 1;
            loadDepartmentData();
        });

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);
        toolPanel.add(btnPanel, BorderLayout.EAST);

        add(toolPanel, BorderLayout.NORTH);

        // 表格设置
        String[] columnNames = {"部门ID", "部门名称", "部门经理", "部门简介"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        departmentTable = new JTable(tableModel);
        departmentTable.setRowHeight(35);
        departmentTable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        departmentTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 15));

        // 列宽设置
        departmentTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        departmentTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        departmentTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        departmentTable.getColumnModel().getColumn(3).setPreferredWidth(300);

        // 双击查看详情
        departmentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
                    int row = departmentTable.rowAtPoint(evt.getPoint());
                    if (row != -1) {
                        int modelRow = departmentTable.convertRowIndexToModel(row);
                        int deptId = (int) tableModel.getValueAt(modelRow, 0);
                        DepartmentService service = new DepartmentService();
                        Department dept = service.getDepartmentById(deptId);
                        if (dept != null) {
                            new DepartmentDetailDialog(SwingUtil.getParentFrame(DepartmentPanel.this),
                                    "部门详情", dept).setVisible(true);
                        }
                    }
                }
            }
        });

        // 表头排序
        departmentTable.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = departmentTable.columnAtPoint(e.getPoint());
                if (column == 0 || column == 1) { // 只允许ID和名称排序
                    if (column == sortColumn) {
                        ascending = !ascending;
                    } else {
                        sortColumn = column;
                        ascending = true;
                    }
                    currentPage = 1;
                    loadDepartmentData();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(departmentTable);
        add(scrollPane, BorderLayout.CENTER);

        // 分页控件
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paginationPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        prevBtn = new JButton("上一页");
        prevBtn.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                loadDepartmentData();
            }
        });

        pageInfoLabel = new JLabel("第 1 页，共 0 页");

        nextBtn = new JButton("下一页");
        nextBtn.addActionListener(e -> {
            int totalPages = (totalRecords + pageSize - 1) / pageSize;
            if (currentPage < totalPages) {
                currentPage++;
                loadDepartmentData();
            }
        });

        paginationPanel.add(prevBtn);
        paginationPanel.add(pageInfoLabel);
        paginationPanel.add(nextBtn);
        add(paginationPanel, BorderLayout.SOUTH);
    }

    // 加载部门数据
    private void loadDepartmentData() {
        tableModel.setRowCount(0);
        List<Department> departments = getDepartmentsFromDB();

        for (Department dept : departments) {
            tableModel.addRow(new Object[]{
                    dept.getId(),
                    dept.getName(),
                    dept.getManager(),
                    dept.getIntro()
            });
        }

        updatePageInfo();
    }

    // 从数据库获取部门数据
    private List<Department> getDepartmentsFromDB() {
        List<Department> departments = new ArrayList<>();
        String sortField = sortColumn == 0 ? "id" : "name";
        String order = ascending ? "ASC" : "DESC";
        int offset = (currentPage - 1) * pageSize;

        String countSql = "SELECT COUNT(*) FROM department";
        String dataSql = String.format(
                "SELECT id, name, manager, intro FROM department ORDER BY %s %s LIMIT %d, %d",
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
            ResultSet dataRs = dataStmt.executeQuery();
            while (dataRs.next()) {
                departments.add(new Department(
                        dataRs.getInt("id"),
                        dataRs.getString("name"),
                        dataRs.getString("manager"),
                        dataRs.getString("intro")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载部门数据失败: " + e.getMessage());
        }
        return departments;
    }

    // 搜索部门
    private void searchDepartments(ActionEvent e) {
        String keyword = searchField.getText().trim();
        tableModel.setRowCount(0);
        String sql = "SELECT id, name, manager, intro FROM department " +
                "WHERE name LIKE ? OR manager LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String param = "%" + keyword + "%";
            pstmt.setString(1, param);
            pstmt.setString(2, param);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("manager"),
                        rs.getString("intro")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "搜索失败: " + ex.getMessage());
        }
        updatePageInfo();
    }

    // 修改添加部门方法，调用服务层
    private void addDepartment(ActionEvent e) {
        DepartmentDialog dialog = new DepartmentDialog(SwingUtil.getParentFrame(this), "添加部门", null);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            DepartmentService service = new DepartmentService();
            boolean success = service.addDepartment(dialog.getDepartment());
            if (success) {
                JOptionPane.showMessageDialog(this, "添加成功");
                loadDepartmentData(); // 刷新表格
            } else {
                JOptionPane.showMessageDialog(this, "添加失败，可能是ID已存在", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 编辑部门
    // 直接在editDepartment方法中内联调用，简化逻辑：
    private void editDepartment(ActionEvent e) {
        int selectedRow = departmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要编辑的部门", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 获取选中部门的ID（修复行索引转换，避免模型与视图行号不一致）
        int modelRow = departmentTable.convertRowIndexToModel(selectedRow);
        Integer deptId = (Integer) tableModel.getValueAt(modelRow, 0);
        if (deptId == null) {
            JOptionPane.showMessageDialog(this, "部门ID为空，无法编辑", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 直接调用Service查询，无冗余封装
        DepartmentService service = new DepartmentService();
        Department dept = service.getDepartmentById(deptId); // 这里调用修复后的方法，无递归
        if (dept == null) {
            JOptionPane.showMessageDialog(this, "部门数据不存在", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 显示编辑对话框
        DepartmentDialog dialog = new DepartmentDialog(SwingUtil.getParentFrame(this), "编辑部门", dept);
        dialog.setVisible(true);

        // 确认修改后更新数据
        if (dialog.isConfirmed()) {
            boolean success = service.updateDepartment(dialog.getDepartment());
            if (success) {
                JOptionPane.showMessageDialog(this, "修改成功");
                loadDepartmentData(); // 刷新表格
            } else {
                JOptionPane.showMessageDialog(this, "修改失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 删除部门
    private void deleteDepartment(ActionEvent e) {
        int selectedRow = departmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的部门", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 获取选中部门的ID和名称
        int modelRow = departmentTable.convertRowIndexToModel(selectedRow);
        int deptId = (int) tableModel.getValueAt(modelRow, 0);
        String deptName = (String) tableModel.getValueAt(modelRow, 1);

        // 确认删除
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "确定要删除部门【" + deptName + "】吗？\n删除后相关数据将无法恢复",
                "确认删除",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            DepartmentService service = new DepartmentService();
            boolean success = service.deleteDepartment(deptId);
            if (success) {
                JOptionPane.showMessageDialog(this, "删除成功");
                loadDepartmentData(); // 刷新表格
            }
        }
    }

    // 获取部门详情


    // 更新分页信息
    private void updatePageInfo() {
        int totalPages = (totalRecords + pageSize - 1) / pageSize;
        pageInfoLabel.setText(String.format("第 %d 页，共 %d 页", currentPage, totalPages));
        prevBtn.setEnabled(currentPage > 1);
        nextBtn.setEnabled(currentPage < totalPages);
    }
}