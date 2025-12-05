package com.pms.gui;

import com.pms.gui.panels.DashboardPanel;
import com.pms.gui.panels.DepartmentPanel;
import com.pms.gui.panels.EmployeePanel;
import com.pms.gui.panels.PersonnelChangePanel;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;

    public MainFrame() {
        initUI();
        setTitle("人事管理系统");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initUI() {
        // 创建主面板，使用边界布局
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 创建左侧导航面板
        JPanel navPanel = createNavPanel();
        mainPanel.add(navPanel, BorderLayout.WEST);

        // 创建内容面板，使用卡片布局
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // 添加各个功能面板
        contentPanel.add(new DashboardPanel(), "dashboard");
        contentPanel.add(new EmployeePanel(), "employee");
        contentPanel.add(new DepartmentPanel(), "department");
        contentPanel.add(new PersonnelChangePanel(), "personnelChange");

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // 添加顶部信息栏
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(new JLabel("欢迎使用人事管理系统"));
        JButton logoutBtn = new JButton("退出登录");
        logoutBtn.addActionListener(e -> logout());
        topPanel.add(logoutBtn);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        add(mainPanel);

        // 默认显示仪表盘
        cardLayout.show(contentPanel, "dashboard");
    }

    // 其余代码保持不变...
    private JPanel createNavPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setPreferredSize(new Dimension(150, 0));
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        // 创建导航按钮
        JButton dashboardBtn = createNavButton("仪表盘");
        JButton employeeBtn = createNavButton("员工管理");
        JButton departmentBtn = createNavButton("部门管理");
        JButton changeBtn = createNavButton("人事变动");

        // 添加按钮点击事件
        dashboardBtn.addActionListener(e -> cardLayout.show(contentPanel, "dashboard"));
        employeeBtn.addActionListener(e -> cardLayout.show(contentPanel, "employee"));
        departmentBtn.addActionListener(e -> cardLayout.show(contentPanel, "department"));
        changeBtn.addActionListener(e -> cardLayout.show(contentPanel, "personnelChange"));

        // 添加按钮到导航面板
        navPanel.add(dashboardBtn);
        navPanel.add(Box.createVerticalStrut(5));
        navPanel.add(employeeBtn);
        navPanel.add(Box.createVerticalStrut(5));
        navPanel.add(departmentBtn);
        navPanel.add(Box.createVerticalStrut(5));
        navPanel.add(changeBtn);
        navPanel.add(Box.createVerticalGlue()); // 填充剩余空间

        return navPanel;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(120, 40));
        button.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        return button;
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "确定要退出登录吗？", "确认", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new LoginFrame().setVisible(true);
        }
    }
}