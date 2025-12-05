package com.pms.gui.dialogs;

import com.pms.model.PersonnelChange;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonnelChangeDialog extends JDialog {
    private boolean confirmed = false;
    private PersonnelChange change;

    private JTextField idField;
    private JTextField employeeIdField;
    private JTextField employeeNameField;
    private JComboBox<String> changeTypeCombo;
    private JTextArea descriptionArea;
    private JTextField timeField;

    public PersonnelChangeDialog(Frame parent, String title) {
        super(parent, title, true);
        this.change = new PersonnelChange();
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

        // 记录ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("记录ID:"), gbc);

        gbc.gridx = 1;
        idField = new JTextField(15);
        formPanel.add(idField, gbc);

        // 员工ID
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("员工ID:"), gbc);

        gbc.gridx = 1;
        employeeIdField = new JTextField(15);
        formPanel.add(employeeIdField, gbc);

        // 员工姓名
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("员工姓名:"), gbc);

        gbc.gridx = 1;
        employeeNameField = new JTextField(15);
        formPanel.add(employeeNameField, gbc);

        // 变动类型
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("变动类型:"), gbc);

        gbc.gridx = 1;
        changeTypeCombo = new JComboBox<>(new String[]{"新员工加入", "职务变动", "辞退"});
        formPanel.add(changeTypeCombo, gbc);

        // 变动时间
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("变动时间:"), gbc);

        gbc.gridx = 1;
        timeField = new JTextField(15);
        timeField.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        formPanel.add(timeField, gbc);

        // 变动描述
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("变动描述:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setLineWrap(true);
        formPanel.add(new JScrollPane(descriptionArea), gbc);

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
    }

    private void confirmAction(ActionEvent e) {
        // 数据验证
        if (idField.getText().trim().isEmpty() || employeeIdField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "记录ID和员工ID不能为空", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 保存数据
        try {
            change.setId(Integer.parseInt(idField.getText().trim()));
            change.setEmployeeId(Integer.parseInt(employeeIdField.getText().trim()));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID必须是数字", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        change.setEmployeeName(employeeNameField.getText().trim());
        change.setChangeType((String) changeTypeCombo.getSelectedItem());
        change.setDescription(descriptionArea.getText().trim());


        confirmed = true;
        dispose();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public PersonnelChange getPersonnelChange() {
        return change;
    }
}