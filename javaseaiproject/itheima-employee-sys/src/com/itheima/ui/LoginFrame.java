package com.itheima.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 登录界面类
public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("员工管理系统 - 登录");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("员工管理系统");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1; gbc.gridy = 1;
        panel.add(new JLabel("用户名:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("密码:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("登录");
        JButton registerButton = new JButton("注册");
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(loginButton, gbc);
        gbc.gridx = 1;
        panel.add(registerButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRegister();
            }
        });

        add(panel);
    }

    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入用户名和密码！", "登录失败", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 这里可以添加实际的用户验证逻辑
        // 模拟验证：用户名admin，密码123456
        if ("admin".equals(username) && "123456".equals(password)) {
            dispose(); // 关闭登录窗口
            SwingUtilities.invokeLater(() -> new EmployeeManagementFrame().setVisible(true));
        } else {
            JOptionPane.showMessageDialog(this, "用户名或密码错误！", "登录失败", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performRegister() {
        // 注册功能示例（简化处理）
        String username = JOptionPane.showInputDialog(this, "请输入新用户名:");
        if (username == null || username.trim().isEmpty()) return;

        String password = JOptionPane.showInputDialog(this, "请输入新密码:");
        if (password == null || password.trim().isEmpty()) return;

        // 实际项目中应将用户信息存储到数据库
        JOptionPane.showMessageDialog(this, "注册成功！用户名: " + username, "注册成功", JOptionPane.INFORMATION_MESSAGE);
    }
}



