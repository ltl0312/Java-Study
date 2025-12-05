package com.pms.gui.panels;

import com.pms.utils.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        initUI();
    }

    // 修改initUI()方法中的布局和间距
    private void initUI() {
        setLayout(new BorderLayout());

        // 顶部标题优化
        JLabel titleLabel = new JLabel("系统概览", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));
        titleLabel.setBackground(new Color(245, 245, 245));
        titleLabel.setOpaque(true);
        add(titleLabel, BorderLayout.NORTH);

        // 统计卡片面板优化
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        statsPanel.setBackground(new Color(249, 249, 249)); // 浅灰背景

        // 添加统计卡片（使用新的配色方案）
        statsPanel.add(createStatCard("总员工数", getTotalEmployeeCount() + " 人",
                new Color(76, 175, 80), new Color(232, 245, 233)));
        statsPanel.add(createStatCard("部门总数", getDepartmentCount() + " 个",
                new Color(33, 150, 243), new Color(227, 242, 253)));
        statsPanel.add(createStatCard("在职员工", getActiveEmployeeCount() + " 人",
                new Color(255, 152, 0), new Color(255, 248, 225)));
        statsPanel.add(createStatCard("人事变动", getPersonnelChanges() + " 条",
                new Color(244, 67, 54), new Color(255, 235, 238)));

        add(statsPanel, BorderLayout.CENTER);

        // 底部信息优化
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 15));
        footerPanel.setBackground(new Color(245, 245, 245));
        JLabel timeLabel = new JLabel("数据更新时间: " +
                java.time.LocalDateTime.now().toString().substring(0, 16));
        timeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        timeLabel.setForeground(new Color(102, 102, 102));
        footerPanel.add(timeLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }

    // 修改createStatCard方法，增加卡片视觉效果
    private JPanel createStatCard(String title, String value, Color valueColor, Color bgColor) {
        JPanel card = new JPanel(new BorderLayout());
        // 卡片阴影和边框效果
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(bgColor); // 卡片背景色
        card.setPreferredSize(new Dimension(0, 120)); // 固定卡片高度

        // 标题
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        titleLabel.setForeground(new Color(102, 102, 102));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        card.add(titleLabel, BorderLayout.NORTH);

        // 数值
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("微软雅黑", Font.BOLD, 28));
        valueLabel.setForeground(valueColor);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    /**
     * 创建统计卡片
     */
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        card.setBackground(Color.WHITE);

        // 标题
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        card.add(titleLabel, BorderLayout.NORTH);

        // 数值
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        valueLabel.setForeground(color);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    // 修复：获取总员工数 - 从person表查询
    private int getTotalEmployeeCount() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) AS total FROM person";
        int count = 0;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取总员工数出错: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt, conn);
        }
        System.out.println("总员工数查询结果: " + count);
        return count;
    }

    // 修复：获取部门总数 - 从department表查询
    private int getDepartmentCount() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) AS dept_count FROM department";
        int count = 0;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("dept_count");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取部门总数出错: " + e.getMessage());
            count = 0;
        } finally {
            closeResources(rs, pstmt, conn);
        }
        System.out.println("部门总数查询结果: " + count);
        return count;
    }

    // 修复：获取在职员工数 - 从person表查询state='t'的记录
    private int getActiveEmployeeCount() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) AS active_count FROM person WHERE state = 't'";
        int count = 0;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("active_count");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取在职员工数出错: " + e.getMessage());
            count = 0;
        } finally {
            closeResources(rs, pstmt, conn);
        }
        System.out.println("在职员工数查询结果: " + count);
        return count;
    }

    // 修复：获取人事变动数 - 从personnel表查询
    private int getPersonnelChanges() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) AS change_count FROM personnel";
        int count = 0;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("change_count");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取人事变动数出错: " + e.getMessage());
            count = 0;
        } finally {
            closeResources(rs, pstmt, conn);
        }
        System.out.println("人事变动数查询结果: " + count);
        return count;
    }

    // 统一资源关闭方法
    private void closeResources(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try {
            if (rs != null) rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) DBConnection.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}