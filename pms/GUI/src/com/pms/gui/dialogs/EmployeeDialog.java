package com.pms.gui.dialogs;

import com.pms.model.Employee;
import com.pms.model.CodeNameItem;
import com.pms.utils.DBConnection;
import com.pms.utils.SwingUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class EmployeeDialog extends JDialog {
    private boolean confirmed = false;
    private Employee employee;

    // 新增：部门/职位/学历下拉框（存储CodeNameItem）
    private JComboBox<CodeNameItem> departmentCombo = new JComboBox<>();
    private JComboBox<CodeNameItem> jobCombo = new JComboBox<>();
    private JComboBox<CodeNameItem> eduLevelCombo = new JComboBox<>();


    // 其他字段保持不变（idField、nameField等）
    private JTextField idField = new JTextField(15);
    private JTextField nameField = new JTextField(15);
    private JRadioButton maleRadio = new JRadioButton("男", true);
    private JRadioButton femaleRadio = new JRadioButton("女");
    private JTextField birthdayField = new JTextField(15);
    private JTextField specialtyField = new JTextField(15);
    private JTextField addressField = new JTextField(15);
    private JTextField telField = new JTextField(15);
    private JTextField emailField = new JTextField(15);
    private JTextArea remarkArea = new JTextArea(3, 15);

    public EmployeeDialog(Frame parent, String title, Employee emp) {
        super(parent, title, true);
        this.employee = emp;
        initUI();
        loadData(); // 加载部门/职位/学历数据
        if (emp != null) {
            loadEmployeeData(); // 编辑模式：回显数据
        }
        pack();
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. 员工ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("员工ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(idField, gbc);

        // 2. 姓名
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("姓名:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        // 3. 性别
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("性别:"), gbc);
        gbc.gridx = 1;
        JPanel sexPanel = new JPanel();
        sexPanel.add(maleRadio);
        sexPanel.add(femaleRadio);
        ButtonGroup sexGroup = new ButtonGroup();
        sexGroup.add(maleRadio);
        sexGroup.add(femaleRadio);
        formPanel.add(sexPanel, gbc);

        // 4. 部门（下拉框，显示名称，存储代码）
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("部门:"), gbc);
        gbc.gridx = 1;
        formPanel.add(departmentCombo, gbc);

        // 5. 职位（下拉框，显示名称，存储代码）
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("职位:"), gbc);
        gbc.gridx = 1;
        formPanel.add(jobCombo, gbc);

        // 6. 学历（下拉框，显示名称，存储代码）
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("学历:"), gbc);
        gbc.gridx = 1;
        formPanel.add(eduLevelCombo, gbc);

        // 其他字段（生日、专业等）按原有逻辑添加...

        // 按钮面板
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okBtn = new JButton("确定");
        JButton cancelBtn = new JButton("取消");
        okBtn.addActionListener(this::confirmAction);
        cancelBtn.addActionListener(e -> dispose());
        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    /**
     * 加载部门、职位、学历数据（从数据库查询代码和名称）
     */
    private void loadData() {
        // 加载部门（department.id 对应 部门代码）
        String deptSql = "SELECT id, name FROM department";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deptSql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                departmentCombo.addItem(new CodeNameItem(
                        rs.getInt("id"),  // 部门代码（int）
                        rs.getString("name")  // 部门名称
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载部门数据失败");
        }

        // 加载职位（job.code 对应 职位代码）
        String jobSql = "SELECT code, description FROM job";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(jobSql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                jobCombo.addItem(new CodeNameItem(
                        rs.getInt("code"),  // 职位代码（1-15）
                        rs.getString("description")
                ));
            }
            // 新增：加载完成后默认选中第一个有效职位
            if (jobCombo.getItemCount() > 0) {
                jobCombo.setSelectedIndex(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载职位数据失败: " + e.getMessage());
        }

        // 加载学历（edu_level.code 对应 学历代码）
        String eduSql = "SELECT code, description FROM edu_level";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(eduSql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                eduLevelCombo.addItem(new CodeNameItem(
                        rs.getInt("code"),  // 学历代码（int）
                        rs.getString("description")  // 学历名称
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载学历数据失败");
        }
    }

    /**
     * 编辑模式：回显员工数据（设置下拉框选中状态）
     */
    private void loadEmployeeData() {
        idField.setText(String.valueOf(employee.getId()));
        idField.setEditable(true); // 编辑时ID可改
        nameField.setText(employee.getName());
        // 性别回显
        if ("女".equals(employee.getSex())) {
            femaleRadio.setSelected(true);
        } else {
            maleRadio.setSelected(true);
        }

        // 部门回显（根据代码选中对应项）
        for (int i = 0; i < departmentCombo.getItemCount(); i++) {
            CodeNameItem item = departmentCombo.getItemAt(i);
            if (item.getCode() == employee.getDepartmentId()) {
                departmentCombo.setSelectedIndex(i);
                break;
            }
        }

        // 职位回显
        for (int i = 0; i < jobCombo.getItemCount(); i++) {
            CodeNameItem item = jobCombo.getItemAt(i);
            if (item.getCode() == employee.getJobCode()) {
                jobCombo.setSelectedIndex(i);
                break;
            }
        }

        // 学历回显
        for (int i = 0; i < eduLevelCombo.getItemCount(); i++) {
            CodeNameItem item = eduLevelCombo.getItemAt(i);
            if (item.getCode() == employee.getEduLevelCode()) {
                eduLevelCombo.setSelectedIndex(i);
                break;
            }
        }

        // 电话回显


        // 其他字段回显（生日、电话等）...
    }

    /**
     * 确认按钮逻辑：封装员工数据（部门/职位/学历用int代码）
     */
    private void confirmAction(ActionEvent e) {
        // 1. 姓名
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "姓名不能为空", "输入错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        employee.setName(name);

        // 2. 性别
        String sex = maleRadio.isSelected() ? "男" : "女";
        employee.setSex(sex);

        // 3. 部门
        CodeNameItem deptItem = (CodeNameItem) departmentCombo.getSelectedItem();
        if (deptItem == null || deptItem.getCode() <= 0) {
            JOptionPane.showMessageDialog(this, "请选择有效的部门", "输入错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        employee.setDepartmentId(deptItem.getCode());

        // 4. 职位
        CodeNameItem jobItem = (CodeNameItem) jobCombo.getSelectedItem();
        if (jobItem == null || jobItem.getCode() <= 0) {
            JOptionPane.showMessageDialog(this, "请选择有效的职位", "输入错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        employee.setJobCode(jobItem.getCode());

        // 5. 学历
        CodeNameItem eduItem = (CodeNameItem) eduLevelCombo.getSelectedItem();
        if (eduItem == null || eduItem.getCode() <= 0) {
            JOptionPane.showMessageDialog(this, "请选择有效的学历", "输入错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        employee.setEduLevelCode(eduItem.getCode());

        // 6. 生日
        try {
            String birthdayStr = birthdayField.getText().trim();
            if (!birthdayStr.isEmpty()) {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                Date birthday = sdf.parse(birthdayStr);
                employee.setBirthday(birthday);
            }
        } catch (java.text.ParseException ex) {
            JOptionPane.showMessageDialog(this, "生日格式错误（正确格式：yyyy-MM-dd）", "输入错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 7. 专业
        employee.setSpecialty(specialtyField.getText().trim());

        // 8. 地址
        employee.setAddress(addressField.getText().trim());

        // 9. 电话
        employee.setTel(telField.getText().trim());

        // 10. 邮箱
        employee.setEmail(emailField.getText().trim());

        // 11. 备注
        employee.setRemark(remarkArea.getText().trim());

        confirmed = true;
        dispose();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Employee getEmployee() {
        return employee;
    }
}