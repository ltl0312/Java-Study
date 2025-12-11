package com.pms.gui.dialogs;

import com.pms.model.PersonnelChange;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class PersonnelChangeDetailDialog extends JDialog {
    private PersonnelChange change;

    public PersonnelChangeDetailDialog(Frame parent, String title, PersonnelChange change) {
        super(parent, title, true);
        this.change = change;
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
        gbc.gridy = 0; // 初始化行号为0

        // 添加所有人事变动信息字段（不可编辑）
        addField(formPanel, gbc, "记录ID:", String.valueOf(change.getId()));
        addField(formPanel, gbc, "员工ID:", String.valueOf(change.getEmployeeId()));
        addField(formPanel, gbc, "员工姓名:", change.getEmployeeName());
        addField(formPanel, gbc, "变动类型:", change.getChangeType());

        // 变动时间格式化
        String changeTime = change.getChangeTime() != null ?
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(change.getChangeTime()) : "";
        addField(formPanel, gbc, "变动时间:", changeTime);

        // 变动描述（多行显示）
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("变动描述:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        JTextArea descriptionArea = new JTextArea(3, 20);
        descriptionArea.setText(change.getDescription() == null ? "" : change.getDescription());
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        formPanel.add(new JScrollPane(descriptionArea), gbc);

        // 确定按钮
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton okBtn = new JButton("确定");
        okBtn.addActionListener(e -> dispose());
        btnPanel.add(okBtn);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    // 工具方法：添加标签和文本框，与EmployeeDetailDialog保持一致
    private void addField(JPanel panel, GridBagConstraints gbc, String labelText, String value) {
        // 标签
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel label = new JLabel(labelText.trim());
        panel.add(label, gbc);

        // 文本框
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField textField = new JTextField(value);
        textField.setEditable(false);
        textField.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        panel.add(textField, gbc);

        // 移至下一行
        gbc.gridy++;
    }
}