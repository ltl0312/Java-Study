// 修改 EmployeePanel.java
package com.pms.gui.panels;

import com.pms.gui.dialogs.EmployeeDialog;
import com.pms.gui.dialogs.EmployeeDetailDialog;
import com.pms.gui.dialogs.PersonnelChangeDialog;
import com.pms.model.CodeNameItem;
import com.pms.model.Employee;
import com.pms.service.EmployeeService;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeePanel extends JPanel {
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    // 当前排序列索引
    private int sortColumn = 0; // 当前排序列索引，默认ID列
    private boolean ascending = true; // 排序方向，默认正序

    // 新增分页相关组件和变量
    private int currentPage = 1;
    private final int pageSize = 15;// 每页显示的记录数
    private int totalRecords = 0;
    private JLabel pageInfoLabel;
    private JButton prevBtn;
    private JButton nextBtn;

    // 新增筛选下拉框
    private JComboBox<CodeNameItem> departmentCombo;
    private JComboBox<CodeNameItem> jobCombo;
    private JComboBox<String> stateCombo;


    public EmployeePanel() {
        initUI();
        loadFilterData(); // 加载筛选数据
        loadEmployeeData(); // 加载员工数据
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // 创建顶部工具栏
        JPanel toolPanel = new JPanel(new BorderLayout());

        // 新增筛选面板
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.add(new JLabel("部门:"));
        departmentCombo = new JComboBox<>();
        departmentCombo.addItem(new CodeNameItem(-1, "全部"));
        filterPanel.add(departmentCombo);

        filterPanel.add(new JLabel("职位:"));
        jobCombo = new JComboBox<>();
        jobCombo.addItem(new CodeNameItem(-1, "全部"));
        filterPanel.add(jobCombo);

        filterPanel.add(new JLabel("状态:"));
        stateCombo = new JComboBox<>(new String[]{"全部", "在职", "离职"});
        filterPanel.add(stateCombo);

        toolPanel.add(filterPanel, BorderLayout.NORTH);

        // 搜索框和按钮面板
        JPanel searchBtnPanel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("搜索:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchBtn = new JButton("搜索");
        searchBtn.addActionListener(this::searchEmployees);
        searchPanel.add(searchBtn);
        searchBtnPanel.add(searchPanel, BorderLayout.WEST);

        // 操作按钮
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addBtn = new JButton("添加");
        JButton editBtn = new JButton("编辑");
        JButton deleteBtn = new JButton("辞退");
        JButton removeBtn = new JButton("删除");
        JButton refreshBtn = new JButton("刷新");

        addBtn.addActionListener(this::addEmployee);
        editBtn.addActionListener(this::editEmployee);
        deleteBtn.addActionListener(this::dimissionEmployee);
        removeBtn.addActionListener(this::deleteEmployee);
        refreshBtn.addActionListener(e -> {
            currentPage = 1; // 刷新时回到第一页
            loadEmployeeData();
        });

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(removeBtn);
        btnPanel.add(refreshBtn);
        searchBtnPanel.add(btnPanel, BorderLayout.EAST);

        toolPanel.add(searchBtnPanel, BorderLayout.CENTER);

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
        // 设置表格行高和字体
        employeeTable.setRowHeight(35); // 增加行高
        employeeTable.setFont(new Font("微软雅黑", Font.PLAIN, 14)); // 增大字体
        employeeTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 15)); // 表头字体加粗

        // 调整表格列宽比例，减少空白
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(90);
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(90);
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(60);
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(110);
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(110);
        employeeTable.getColumnModel().getColumn(5).setPreferredWidth(130);
        employeeTable.getColumnModel().getColumn(6).setPreferredWidth(70);

        // 调整工具栏间距（原代码中toolPanel的布局设置处）
        toolPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // 调整分页面板间距
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paginationPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // 添加表格到滚动面板
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        // 确保表格只能选择单行，避免多选导致的双击判断问题
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 添加双击事件,双击显示员工信息
        // 优化双击事件处理，确保稳定触发
        employeeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                // 1. 验证事件源和点击条件：必须是左键双击且点击在表格内容区域
                if (evt.getButton() != MouseEvent.BUTTON1 || evt.getClickCount() != 2) {
                    return;
                }

                // 2. 确定点击位置是否在有效单元格上
                int row = employeeTable.rowAtPoint(evt.getPoint());
                int col = employeeTable.columnAtPoint(evt.getPoint());
                if (row == -1 || col == -1) { // 点击在表格空白区域
                    return;
                }

                // 3. 确保行被选中（处理表格选择模式可能的影响）
                employeeTable.setRowSelectionInterval(row, row);

                // 4. 转换为模型索引（处理排序/过滤后的索引映射）
                int modelRow = employeeTable.convertRowIndexToModel(row);

                try {
                    // 5. 安全获取员工ID（避免类型转换异常）
                    Object idObj = employeeTable.getModel().getValueAt(modelRow, 0);
                    if (!(idObj instanceof Integer)) {
                        JOptionPane.showMessageDialog(EmployeePanel.this,
                                "无效的员工ID格式", "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int employeeId = (Integer) idObj;

                    // 6. 获取员工信息并显示
                    Employee employee = getEmployeeById(employeeId);
                    if (employee != null) {
                        new EmployeeDetailDialog(
                                SwingUtil.getParentFrame(EmployeePanel.this),
                                "员工详细信息",
                                employee
                        ).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(EmployeePanel.this,
                                "未找到ID为" + employeeId + "的员工信息",
                                "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e) {
                    // 7. 捕获所有异常，确保不会因意外错误导致功能失效
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(EmployeePanel.this,
                            "处理双击事件时发生错误: " + e.getMessage(),
                            "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 添加分页控件
        prevBtn = new JButton("上一页");
        prevBtn.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                loadEmployeeData();
            }
        });

        pageInfoLabel = new JLabel("第 1 页，共 0 页");

        nextBtn = new JButton("下一页");
        nextBtn.addActionListener(e -> {
            int totalPages = (totalRecords + pageSize - 1) / pageSize;
            if (currentPage < totalPages) {
                currentPage++;
                loadEmployeeData();
            }
        });

        paginationPanel.add(prevBtn);
        paginationPanel.add(pageInfoLabel);
        paginationPanel.add(nextBtn);
        add(paginationPanel, BorderLayout.SOUTH);

        // 添加表头点击事件，实现排序
        employeeTable.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = employeeTable.columnAtPoint(e.getPoint());
                // 只处理ID(0)、姓名(1)、部门(3)、职位(4)列
                if (column != 0 && column != 1 && column != 3 && column != 4) {
                    return;
                }

                // 如果点击的是当前排序列，则切换排序方向
                if (column == sortColumn) {
                    ascending = !ascending;
                } else {
                    // 否则设置为新的排序列，默认正序
                    sortColumn = column;
                    ascending = true;
                }

                // 排序时重置到第一页
                currentPage = 1;
                // 重新加载数据（会带上排序条件）
                loadEmployeeData();
            }
        });
    }

    // 加载筛选数据（部门、职位）
    private void loadFilterData() {
        // 加载部门
        String deptSql = "SELECT id, name FROM department";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deptSql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                departmentCombo.addItem(new CodeNameItem(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载部门数据失败");
        }

        // 加载职位
        String jobSql = "SELECT code, description FROM job";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(jobSql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                jobCombo.addItem(new CodeNameItem(
                        rs.getInt("code"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载职位数据失败");
        }

        // 添加筛选事件监听
        departmentCombo.addActionListener(e -> {
            currentPage = 1;
            loadEmployeeData();
        });
        jobCombo.addActionListener(e -> {
            currentPage = 1;
            loadEmployeeData();
        });
        stateCombo.addActionListener(e -> {
            currentPage = 1;
            loadEmployeeData();
        });
    }

    // 加载员工数据（支持分页和筛选）
    private void loadEmployeeData() {
        // 获取筛选条件
        CodeNameItem deptItem = (CodeNameItem) departmentCombo.getSelectedItem();
        int deptId = deptItem.getCode();

        CodeNameItem jobItem = (CodeNameItem) jobCombo.getSelectedItem();
        int jobCode = jobItem.getCode();

        String state = stateCombo.getSelectedItem().toString();
        String keyword = searchField.getText().trim();

        // 转换状态筛选条件（"全部"对应空值）
        String stateParam = "全部".equals(state) ? "" : state;

        // 计算分页偏移量
        int offset = (currentPage - 1) * pageSize;

        // 获取排序字段和方向
        String sortField = getSortField(sortColumn);
        String sortDirection = ascending ? "ASC" : "DESC";

        // 从服务层获取数据（带排序条件）
        EmployeeService service = new EmployeeService();
        List<Employee> employees = service.getEmployeesByCondition(
                keyword, deptId, jobCode, stateParam, offset, pageSize,
                sortField, sortDirection
        );

        // 获取总记录数
        totalRecords = service.getEmployeeCountByCondition(keyword, deptId, jobCode, stateParam);

        // 更新表格数据
        tableModel.setRowCount(0);
        for (Employee emp : employees) {
            Object[] row = {
                    emp.getId(),
                    emp.getName(),
                    emp.getSex(),
                    emp.getDepartmentName(),
                    emp.getJobName(),
                    emp.getTel(),
                    "t".equals(String.valueOf(emp.getState())) ? "在职" : "离职"
            };
            tableModel.addRow(row);
        }

        // 更新分页信息
        int totalPages = (totalRecords + pageSize - 1) / pageSize;
        pageInfoLabel.setText("第 " + currentPage + " 页，共 " + totalPages + " 页");

        // 控制分页按钮状态
        prevBtn.setEnabled(currentPage > 1);
        nextBtn.setEnabled(currentPage < totalPages);
    }

    // 搜索员工
    private void searchEmployees(ActionEvent e) {
        currentPage = 1; // 搜索时回到第一页
        loadEmployeeData();
    }

    // 添加员工 - 改为使用人事变动界面的新员工加入功能
    private void addEmployee(ActionEvent e) {
        // 创建并显示人事变动对话框，设置为"新员工加入"类型
        PersonnelChangeDialog dialog = new PersonnelChangeDialog(
                SwingUtil.getParentFrame(this),
                "添加新员工"
        );

        // 自动选择"新员工加入"类型
        dialog.getChangeTypeCombo().setSelectedItem("新员工加入");
        
        dialog.setVisible(true);

        // 如果用户点击了确认按钮，员工会通过人事变动自动添加
        if (dialog.isConfirmed()) {
            SwingUtil.showInfoDialog(this, "员工添加成功");
            loadEmployeeData(); // 刷新数据
        }
    }

    private void editEmployee(ActionEvent e) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要编辑的员工", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int employeeId = (int) employeeTable.getValueAt(selectedRow, 0);
        // 从数据库加载完整员工信息
        Employee employee = getEmployeeById(employeeId);
        if (employee == null) {
            JOptionPane.showMessageDialog(this, "获取员工信息失败", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 修改：使用人事变动界面进行编辑操作
        PersonnelChangeDialog dialog = new PersonnelChangeDialog(SwingUtil.getParentFrame(this), "编辑员工信息");
        // 设置为部门变动或职务变动
        dialog.getChangeTypeCombo().setSelectedItem("职务变动");
        // 设置员工信息
        dialog.getEmployeeIdField().setText(String.valueOf(employee.getId()));
        dialog.getEmployeeNameField().setText(employee.getName());
        
        // 加载所有部门数据
        try {
            // 先找到原部门的CodeNameItem
            CodeNameItem originalDeptItem = null;
            CodeNameItem originalJobItem = null;
            
            // 遍历部门下拉框找到原部门
            for (int i = 0; i < dialog.getDepartmentCombo().getItemCount(); i++) {
                CodeNameItem item = dialog.getDepartmentCombo().getItemAt(i);
                if (item.getCode() == employee.getDepartmentId()) {
                    originalDeptItem = item;
                    break;
                }
            }
            
            // 设置原部门和新部门为当前部门
            if (originalDeptItem != null) {
                dialog.getDepartmentCombo().setSelectedItem(originalDeptItem);
                // 加载职位列表
                List<CodeNameItem> jobs = dialog.loadJobsByDepartment(employee.getDepartmentId());
                DefaultComboBoxModel<CodeNameItem> jobModel = new DefaultComboBoxModel<>(jobs.toArray(new CodeNameItem[0]));
                dialog.getJobCombo().setModel(jobModel);
                
                // 遍历职位列表找到原职位
                for (CodeNameItem jobItem : jobs) {
                    if (jobItem.getCode() == employee.getJobCode()) {
                        originalJobItem = jobItem;
                        break;
                    }
                }
                
                if (originalJobItem != null) {
                    dialog.getJobCombo().setSelectedItem(originalJobItem);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载部门和职位数据失败：" + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
        
        // 设置员工ID和姓名为不可编辑
        dialog.getEmployeeIdField().setEditable(false);
        dialog.getEmployeeNameField().setEditable(false);
        // 显示对话框
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            SwingUtil.showInfoDialog(this, "更新成功");
            loadEmployeeData(); // 刷新数据
        }
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

    // 添加获取员工详情的方法
    private Employee getEmployeeById(int employeeId) {
        EmployeeService service = new EmployeeService();
        return service.getEmployeeById(employeeId);
    }
    private void dimissionEmployee(ActionEvent e) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要辞退的员工", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int employeeId = (int) employeeTable.getValueAt(selectedRow, 0);
        String employeeName = (String) employeeTable.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "确定要辞退员工 " + employeeName + " 吗？",
                "确认辞退",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // 使用人事变动界面进行辞退操作
            PersonnelChangeDialog dialog = new PersonnelChangeDialog(SwingUtil.getParentFrame(this), "辞退员工");
            // 设置为辞退变动类型
            dialog.getChangeTypeCombo().setSelectedItem("辞退");
            // 设置员工信息
            dialog.getEmployeeIdField().setText(String.valueOf(employeeId));
            dialog.getEmployeeNameField().setText(employeeName);
            // 设置变动描述
            dialog.getDescriptionArea().setText("员工被辞退");
            // 设置员工ID和姓名为不可编辑
            dialog.getEmployeeIdField().setEditable(false);
            dialog.getEmployeeNameField().setEditable(false);
            // 显示对话框
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                // 刷新表格数据，显示员工状态变化
                loadEmployeeData();
                SwingUtil.showInfoDialog(this, "员工已辞退");
            }
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
                "确定要从数据库中删除员工 " + employeeName + " 的所有信息吗？\n此操作不可恢复！",
                "确认删除",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            EmployeeService service = new EmployeeService();
            boolean success = service.deleteEmployee(employeeId);
            if (success) {
                // 从表格中移除该行
                tableModel.removeRow(selectedRow);
                SwingUtil.showInfoDialog(this, "员工信息已从数据库中删除");
            } else {
                SwingUtil.showErrorDialog(this, "删除员工信息失败");
            }
        }
    }

    // 添加分页信息更新方法
    private void updatePaginationInfo() {
        // 实际应用中应该从数据库查询总记录数
        totalRecords = new EmployeeService().getEmployeeCount(
                searchField.getText().trim(),
                ((CodeNameItem)departmentCombo.getSelectedItem()).getCode(),
                ((CodeNameItem)jobCombo.getSelectedItem()).getCode(),
                "全部".equals((String)stateCombo.getSelectedItem()) ? "" : (String)stateCombo.getSelectedItem()
        );

        int totalPages = (totalRecords + pageSize - 1) / pageSize;
        pageInfoLabel.setText("第 " + currentPage + " 页，共 " + totalPages + " 页");

        prevBtn.setEnabled(currentPage > 1);
        nextBtn.setEnabled(currentPage < totalPages);
    }

    // 添加排序字段映射方法
    private String getSortField(int column) {
        switch (column) {
            case 0: return "p.id";          // ID列对应数据库字段
            case 1: return "p.name";        // 姓名字段
            case 3: return "d.name";        // 部门名称
            case 4: return "j.description"; // 职位名称
            default: return "p.id";         // 默认按ID排序
        }
    }

}