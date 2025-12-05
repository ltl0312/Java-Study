package com.pms.gui.panels;

import com.pms.gui.dialogs.EmployeeDetailDialog;
import com.pms.gui.dialogs.EmployeeDialog;
import com.pms.model.Employee;
import com.pms.service.EmployeeService;
import com.pms.utils.DBConnection;
import com.pms.utils.SwingUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeePanel extends JPanel {
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public EmployeePanel() {
        initUI();
        loadEmployeeData(); // 加载员工数据
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // 创建顶部工具栏
        JPanel toolPanel = new JPanel(new BorderLayout());

        // 搜索框
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("搜索:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchBtn = new JButton("搜索");
        searchBtn.addActionListener(this::searchEmployees);
        searchPanel.add(searchBtn);
        toolPanel.add(searchPanel, BorderLayout.WEST);

        // 操作按钮
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addBtn = new JButton("添加");
        JButton editBtn = new JButton("编辑");
        JButton deleteBtn = new JButton("删除");
        JButton refreshBtn = new JButton("刷新");

        addBtn.addActionListener(this::addEmployee);
        editBtn.addActionListener(this::editEmployee);
        deleteBtn.addActionListener(this::deleteEmployee);
        refreshBtn.addActionListener(e -> loadEmployeeData());

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);
        toolPanel.add(btnPanel, BorderLayout.EAST);

        add(toolPanel, BorderLayout.NORTH);

        // 创建表格
        String[] columnNames = {"员工ID", "姓名", "性别", "部门", "职务", "联系电话", "状态"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 表格不可编辑
            }
        };

        employeeTable = new JTable(tableModel);
        // 设置表格列宽
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        employeeTable.getColumnModel().getColumn(5).setPreferredWidth(120);
        employeeTable.getColumnModel().getColumn(6).setPreferredWidth(60);

        // 添加表格到滚动面板
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        // 添加双击事件,双击显示员工信息
        employeeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { // 双击事件
                    int selectedRow = employeeTable.getSelectedRow();
                    if (selectedRow != -1) {
                        int employeeId = (int) employeeTable.getValueAt(selectedRow, 0);
                        Employee employee = getEmployeeById(employeeId);
                        if (employee != null) {
                            // 显示详情对话框
                            new EmployeeDetailDialog(
                                    SwingUtil.getParentFrame(EmployeePanel.this),
                                    "员工详细信息",
                                    employee
                            ).setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(EmployeePanel.this,
                                    "无法获取员工详细信息", "错误", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

    }

    // 加载员工数据（实际项目中应从数据库加载）
    private void loadEmployeeData() {
        // 清空表格
        tableModel.setRowCount(0);

        // 模拟数据，实际应从数据库查询
        List<Employee> employees = getSampleEmployees();

        // 添加数据到表格
        for (Employee emp : employees) {
            Object[] rowData = {
                    emp.getId(),
                    emp.getName(),
                    emp.getSex(),
                    emp.getDepartmentId(),
                    emp.getJobCode(),
                    emp.getTel(),
                    emp.getState() == 't' ? "在职" : "离职"  // 正确：char与char比较
            };
            tableModel.addRow(rowData);
        }
    }

    // 模拟员工数据
    private List<Employee> getSampleEmployees() {
        String sql = "SELECT id, name, sex, department, job, state, tel FROM person";
        List<Employee> employees = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // 提取字段值（同样需与数据库表列名一致）
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String sex = rs.getString("sex");
                int department = rs.getInt("department");
                int job = rs.getInt("job");
                String state = rs.getString("state");
                String tel = rs.getString("tel");

                // 3. 批量添加到列表（无需逐个写 add 语句）
                Employee emp = new Employee(id, name, sex, department, job, tel);
                emp.setState(state != null && state.length() > 0 ? state.charAt(0) : 't');
                employees.add(emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }

    // 搜索员工
    private void searchEmployees(ActionEvent e) {
        String keyword = searchField.getText().trim();
        // 实际项目中应根据关键字查询数据库
        JOptionPane.showMessageDialog(this, "搜索功能待实现: " + keyword);
    }

    private void addEmployee(ActionEvent e) {
        EmployeeDialog dialog = new EmployeeDialog(SwingUtil.getParentFrame(this), "添加员工", null);
        dialog.setVisible(true);
        EmployeeService employeeService = new EmployeeService();
        if (dialog.isConfirmed()) {
            Employee employee = dialog.getEmployee();
            if (employeeService.addEmployee(employee)) {  // 调用服务类添加方法
                SwingUtil.showInfoDialog(this, "添加成功");
                loadEmployeeData();
            } else {
                SwingUtil.showErrorDialog(this, "添加失败");
            }
        }
    }

    private void editEmployee(ActionEvent e) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要编辑的员工", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int employeeId = (int) employeeTable.getValueAt(selectedRow, 0);
        // 修复：从数据库加载完整员工信息（替换原代码中手动创建Employee的部分）
        Employee employee = getEmployeeById(employeeId);
        if (employee == null) {
            JOptionPane.showMessageDialog(this, "获取员工信息失败", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        EmployeeDialog dialog = new EmployeeDialog(SwingUtil.getParentFrame(this), "编辑员工", employee);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            // 新增：调用更新方法
            Employee updatedEmployee = dialog.getEmployee();
            EmployeeService employeeService = new EmployeeService();
            if (employeeService.updateEmployee(updatedEmployee)) {
                SwingUtil.showInfoDialog(this, "更新成功");
                loadEmployeeData(); // 刷新数据
            } else {
                SwingUtil.showErrorDialog(this, "更新失败");
            }
        }
    }

    private Employee getEmployeeById(int employeeId) {
        String sql = "SELECT * FROM person WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setName(rs.getString("name"));
                emp.setSex(rs.getString("sex"));
                emp.setBirthday(rs.getDate("birthday"));
                emp.setDepartmentId(rs.getInt("department"));
                emp.setJobCode(rs.getInt("job"));
                emp.setEduLevelCode(rs.getInt("edu_level"));
                emp.setSpecialty(rs.getString("specialty"));
                emp.setAddress(rs.getString("address"));
                emp.setTel(rs.getString("tel")); // 确保电话被加载
                emp.setEmail(rs.getString("email"));
                emp.setState(rs.getString("state") != null && !rs.getString("state").isEmpty()
                        ? rs.getString("state").charAt(0) : 't');
                emp.setRemark(rs.getString("remark"));

                // 加载关联名称（部门、职位、学历）
                loadRelatedNames(emp);

                return emp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 新增：加载关联表的名称信息
    private void loadRelatedNames(Employee emp) {
        try {
            // 加载部门名称
            String deptSql = "SELECT name FROM department WHERE id = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(deptSql)) {
                pstmt.setInt(1, emp.getDepartmentId());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    emp.setDepartmentName(rs.getString("name"));
                }
            }

            // 加载职位名称
            String jobSql = "SELECT description FROM job WHERE code = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(jobSql)) {
                pstmt.setInt(1, emp.getJobCode());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    emp.setJobName(rs.getString("description"));
                }
            }

            // 加载学历名称
            String eduSql = "SELECT description FROM edu_level WHERE code = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(eduSql)) {
                pstmt.setInt(1, emp.getEduLevelCode());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    emp.setEduLevelName(rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteEmployee(ActionEvent e) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的员工", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int employeeId = (int) employeeTable.getValueAt(selectedRow, 0);
        String employeeName = (String) employeeTable.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "确定要删除员工 " + employeeName + " 吗？",
                "确认删除",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            EmployeeService employeeService = new EmployeeService();
            if (employeeService.deleteEmployee(employeeId)) {  // 调用服务类删除方法
                tableModel.removeRow(selectedRow);
                SwingUtil.showInfoDialog(this, "员工已删除");
            } else {
                SwingUtil.showErrorDialog(this, "删除失败");
            }
        }
    }
}