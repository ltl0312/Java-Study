package com.pms.gui.panels;

import com.pms.gui.dialogs.DepartmentDialog;
import com.pms.model.Department;
import com.pms.utils.SwingUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class DepartmentPanel extends JPanel {
    private JTable departmentTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public DepartmentPanel() {
        initUI();
        loadDepartmentData();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // 顶部工具栏
        JPanel toolPanel = new JPanel(new BorderLayout());

        // 搜索框
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
        refreshBtn.addActionListener(e -> loadDepartmentData());

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);
        toolPanel.add(btnPanel, BorderLayout.EAST);

        add(toolPanel, BorderLayout.NORTH);

        // 创建表格
        String[] columnNames = {"部门ID", "部门名称", "部门经理", "部门简介"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 表格不可编辑
            }
        };

        departmentTable = new JTable(tableModel);
        // 设置列宽
        departmentTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        departmentTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        departmentTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        departmentTable.getColumnModel().getColumn(3).setPreferredWidth(300);

        JScrollPane scrollPane = new JScrollPane(departmentTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    // 加载部门数据
    private void loadDepartmentData() {
        tableModel.setRowCount(0); // 清空表格
        List<Department> departments = getSampleDepartments(); // 模拟数据

        for (Department dept : departments) {
            Object[] rowData = {
                    dept.getId(),
                    dept.getName(),
                    dept.getManager(),
                    dept.getIntro()
            };
            tableModel.addRow(rowData);
        }
    }

    // 模拟部门数据
    private List<Department> getSampleDepartments() {
        List<Department> departments = new ArrayList<>();
        departments.add(new Department(01, "技术部", "张伟", "负责AI研发、系统运维及技术创新"));
        departments.add(new Department(02, "人事部", "李静", "负责人力资源规划与人才发展"));
        departments.add(new Department(03, "财务部", "王丽", "负责财务核算、税务筹划及资金管理"));
        departments.add(new Department(04, "市场部", "刘强", "负责品牌营销、渠道拓展及客户管理"));
        departments.add(new Department(05, "行政部", "赵敏", "负责后勤保障、资产管理及会议服务"));
        return departments;
    }

    // 搜索部门
    private void searchDepartments(ActionEvent e) {
        String keyword = searchField.getText().trim();
        // 实际项目中应根据关键字查询数据库
        JOptionPane.showMessageDialog(this, "搜索部门: " + keyword);
    }

    // 添加部门
    private void addDepartment(ActionEvent e) {
        DepartmentDialog dialog = new DepartmentDialog(SwingUtil.getParentFrame(this), "添加部门", null);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            loadDepartmentData(); // 刷新数据
        }
    }

    // 编辑部门
    private void editDepartment(ActionEvent e) {
        int selectedRow = departmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要编辑的部门", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int deptId = (int) departmentTable.getValueAt(selectedRow, 0);
        Department dept = new Department(
                deptId,
                (String) departmentTable.getValueAt(selectedRow, 1),
                (String) departmentTable.getValueAt(selectedRow, 2),
                (String) departmentTable.getValueAt(selectedRow, 3)
        );

        DepartmentDialog dialog = new DepartmentDialog(SwingUtil.getParentFrame(this), "编辑部门", dept);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            loadDepartmentData();
        }
    }

    // 删除部门
    private void deleteDepartment(ActionEvent e) {
        int selectedRow = departmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的部门", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String deptName = (String) departmentTable.getValueAt(selectedRow, 1);
        if (SwingUtil.showConfirmDialog(this, "确定要删除部门 " + deptName + " 吗？")) {
            tableModel.removeRow(selectedRow);
            SwingUtil.showInfoDialog(this, "部门已删除");
        }
    }
}