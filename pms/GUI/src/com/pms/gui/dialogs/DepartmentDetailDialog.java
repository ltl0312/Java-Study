package com.pms.gui.dialogs;

import com.pms.model.Department;

import javax.swing.*;
import java.awt.*;

public class DepartmentDetailDialog extends JDialog {
    private Department department;

    public DepartmentDetailDialog(Frame parent, String title, Department dept) {
        super(parent, title, true);
        this.department = dept;
        initUI();
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
        gbc.gridy = 0; // 初始化行号为0，解决重叠问题

        // 添加部门信息字段（不可编辑）
        addField(formPanel, gbc, "部门ID:", String.valueOf(department.getId()));
        addField(formPanel, gbc, "部门名称:", department.getName());
        addField(formPanel, gbc, "部门经理:", department.getManager());

        // 部门简介（多行显示）
        gbc.gridx = 0;
        gbc.gridy++; // 使用当前行号并递增
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("部门简介:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        JTextArea introArea = new JTextArea(5, 20);
        introArea.setText(department.getIntro() == null ? "" : department.getIntro());
        introArea.setLineWrap(true);
        introArea.setEditable(false);
        formPanel.add(new JScrollPane(introArea), gbc);

        // 确定按钮
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton okBtn = new JButton("确定");
        okBtn.addActionListener(e -> dispose());
        btnPanel.add(okBtn);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    // 工具方法：添加字段到表单
    private void addField(JPanel panel, GridBagConstraints gbc, String label, String value) {
        // 标签配置
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);

        // 文本框配置
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField field = new JTextField(value);
        field.setEditable(false);
        panel.add(field, gbc);

        // 移至下一行（关键修复：确保每次添加后行号递增）
        gbc.gridy++;
    }
}