package com.pms.gui.dialogs;

import com.pms.model.Department;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DepartmentDialog extends JDialog {
    private boolean confirmed = false;
    private Department department;

    private JTextField idField;
    private JTextField nameField;
    private JTextField managerField;
    private JTextArea introArea;

    public DepartmentDialog(Frame parent, String title, Department dept) {
        super(parent, title, true);
        this.department = dept;
        initUI();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 表单面板
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 部门ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("部门ID:"), gbc);

        gbc.gridx = 1;
        idField = new JTextField(15);
        idField.setEditable(department == null); // 编辑时不可修改ID
        formPanel.add(idField, gbc);

        // 部门名称
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("部门名称:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(15);
        formPanel.add(nameField, gbc);

        // 部门经理
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("部门经理:"), gbc);

        gbc.gridx = 1;
        managerField = new JTextField(15);
        formPanel.add(managerField, gbc);

        // 部门简介
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("部门简介:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        introArea = new JTextArea(5, 20);
        introArea.setLineWrap(true);
        formPanel.add(new JScrollPane(introArea), gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // 按钮面板
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okBtn = new JButton("确定");
        JButton cancelBtn = new JButton("取消");

        okBtn.addActionListener(this::confirmAction);
        cancelBtn.addActionListener(e -> dispose());

        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // 加载部门数据（编辑模式）
        if (department != null) {
            idField.setText(String.valueOf(department.getId()));
            nameField.setText(department.getName());
            managerField.setText(department.getManager());
            introArea.setText(department.getIntro());
        }
    }

    private void confirmAction(ActionEvent e) {
        // 数据验证
        if (idField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入部门ID", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入部门名称", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 保存数据
        if (department == null) {
            department = new Department();
        }
        try {
            department.setId(Integer.parseInt(idField.getText().trim()));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "部门ID必须是数字", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        department.setName(nameField.getText().trim());
        department.setManager(managerField.getText().trim());
        department.setIntro(introArea.getText().trim());

        confirmed = true;
        dispose();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Department getDepartment() {
        return department;
    }
}