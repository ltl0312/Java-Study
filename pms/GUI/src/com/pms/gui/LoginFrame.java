package com.pms.gui;

import com.pms.utils.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField idField;         // 员工ID输入框
    private JPasswordField passwordField; // 密码输入框
    private int loginUserId;
    private String loginUserAuthority;
    private int departmentId;

    // 构造方法：初始化登录窗口
    public LoginFrame() {
        initUI();                      // 初始化界面组件
        setTitle("人事管理系统 - 登录");  // 设置窗口标题
        setSize(400, 300);             // 设置窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭窗口时退出程序
        setLocationRelativeTo(null);   // 窗口居中显示
        setResizable(false);           // 禁止调整窗口大小
    }

    // 初始化界面组件
    private void initUI() {
        // 创建主面板，使用网格袋布局
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 组件间距
        gbc.anchor = GridBagConstraints.WEST; // 组件左对齐

        // 添加标题
        JLabel titleLabel = new JLabel("人事管理系统");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        gbc.gridx = 0;          // 第0列
        gbc.gridy = 0;          // 第0行
        gbc.gridwidth = 2;      // 跨2列
        gbc.anchor = GridBagConstraints.CENTER; // 居中对齐
        panel.add(titleLabel, gbc);

        // 添加员工ID标签和输入框
        gbc.gridy++;            // 下一行
        gbc.gridwidth = 1;      // 跨1列
        gbc.anchor = GridBagConstraints.WEST; // 左对齐
        panel.add(new JLabel("员工ID:"), gbc);

        gbc.gridx = 1;          // 第1列
        idField = new JTextField(15); // 长度为15的文本框
        panel.add(idField, gbc);

        // 添加密码标签和输入框
        gbc.gridx = 0;          // 第0列
        gbc.gridy++;            // 下一行
        panel.add(new JLabel("密码:"), gbc);

        gbc.gridx = 1;          // 第1列
        passwordField = new JPasswordField(15); // 密码框
        panel.add(passwordField, gbc);

        // 添加按钮
        gbc.gridx = 0;          // 第0列
        gbc.gridy++;            // 下一行
        gbc.gridwidth = 2;      // 跨2列
        gbc.anchor = GridBagConstraints.CENTER; // 居中对齐

        JPanel buttonPanel = new JPanel();
        JButton loginBtn = new JButton("登录");
        JButton resetBtn = new JButton("重置");

        // 绑定按钮事件
        loginBtn.addActionListener(this::loginAction); // 登录事件
        resetBtn.addActionListener(e -> resetFields()); // 重置事件

        buttonPanel.add(loginBtn);
        buttonPanel.add(resetBtn);
        panel.add(buttonPanel, gbc);

        add(panel); // 将主面板添加到窗口
    }

    // 登录事件处理
    private void loginAction(ActionEvent e) {
        String idStr = idField.getText().trim(); // 获取员工ID
        String password = new String(passwordField.getPassword()).trim(); // 获取密码

        // 验证输入不为空
        if (idStr.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入员工ID和密码", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 验证员工ID为数字
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "员工ID必须是数字", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // 验证登录信息
        if (validateLogin(id, password)) {
            JOptionPane.showMessageDialog(this, "登录成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            // 关闭登录窗口，根据权限打开不同窗口
            dispose();
            if ("manager".equals(loginUserAuthority) && departmentId == 2) {
                // 管理员打开主窗口
                new MainFrame().setVisible(true);
            } else {
                // 普通用户打开个人信息窗口
                new PersonalInfoFrame(loginUserId).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "员工ID或密码错误", "错误", JOptionPane.ERROR_MESSAGE);
        }


    }

    // 验证登录信息（数据库查询）
    private boolean validateLogin(int id, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            // 修改SQL，同时查询密码和权限
            String sql = "SELECT password, authority, department FROM person WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                String dbPassword = rs.getString("password");
                loginUserAuthority = rs.getString("authority");
                departmentId = rs.getInt("department");
                if (dbPassword.equals(password)) {
                    loginUserId = id; // 记录登录用户ID
                    return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "数据库错误: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        } finally {
            // 关闭资源代码保持不变
        }
        return false;
    }

    // 重置输入框
    private void resetFields() {
        idField.setText("");
        passwordField.setText("");
    }

}