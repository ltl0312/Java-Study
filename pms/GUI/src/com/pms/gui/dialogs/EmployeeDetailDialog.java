package com.pms.gui.dialogs;

import com.pms.gui.panels.EmployeePanel;
import com.pms.model.Employee;
import com.pms.utils.SwingUtil;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class EmployeeDetailDialog extends JDialog {
    private Employee employee;

    public EmployeeDetailDialog(Frame parent, String title, Employee emp) {
        super(parent, title, true);
        this.employee = emp;
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

        // 添加所有员工信息字段（不可编辑）
        addField(formPanel, gbc, "员工ID:", employee.getId() + "");
        addField(formPanel, gbc, "姓名:", employee.getName());
        addField(formPanel, gbc, "性别:", employee.getSex());
        addField(formPanel, gbc, "部门:", employee.getDepartmentName());
        addField(formPanel, gbc, "职位:", employee.getJobName());
        addField(formPanel, gbc, "学历:", employee.getEduLevelName());
        
        // 出生日期格式化
        String birthday = employee.getBirthday() != null ? 
            new SimpleDateFormat("yyyy-MM-dd").format(employee.getBirthday()) : "";
        addField(formPanel, gbc, "出生日期:", birthday);

        addField(formPanel, gbc, "联系电话:", employee.getTel());
        addField(formPanel, gbc, "邮箱:", employee.getEmail());
        addField(formPanel, gbc, "地址:", employee.getAddress());
        addField(formPanel, gbc, "专业:", employee.getSpecialty());
        addField(formPanel, gbc, "状态:", employee.getState() == 't' ? "在职" : "离职");

        // 备注（多行显示）
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("备注:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        JTextArea remarkArea = new JTextArea(3, 20);
        remarkArea.setText(employee.getRemark() == null ? "" : employee.getRemark());
        remarkArea.setLineWrap(true);
        remarkArea.setEditable(false);
        formPanel.add(new JScrollPane(remarkArea), gbc);

        // 确定按钮
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton okBtn = new JButton("确定");
        okBtn.addActionListener(e -> dispose());
        btnPanel.add(okBtn);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        add(mainPanel);


    }



    // 工具方法：添加标签和文本框
    private void addField(JPanel panel, GridBagConstraints gbc, String labelText, String value) {
        // 标签
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel label = new JLabel(labelText);
        // 确保标签文本正确，没有多余字符
        label.setText(labelText.trim());
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