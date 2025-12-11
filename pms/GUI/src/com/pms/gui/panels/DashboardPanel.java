package com.pms.gui.panels;

import com.pms.utils.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardPanel extends JPanel {
    private JPanel statsPanel;
    private JLabel timeLabel;
    private Timer autoRefreshTimer;

    public DashboardPanel() {
        initUI();
        startAutoRefresh();
    }

    // 修改initUI()方法中的布局和间距
    private void initUI() {
        setLayout(new BorderLayout());

        // 顶部标题优化
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new java.awt.Color(245, 245, 245));
        topPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        topPanel.setOpaque(true);
        
        // 标题标签
        JLabel titleLabel = new JLabel("系统概览", SwingConstants.CENTER);
        titleLabel.setFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 24));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        // 添加按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        
        // 添加手动刷新按钮
        JButton refreshButton = new JButton("刷新");
        refreshButton.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 14));
        refreshButton.setBackground(new java.awt.Color(33, 150, 243));
        refreshButton.setForeground(java.awt.Color.WHITE);
        refreshButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        refreshButton.setFocusPainted(false);
        refreshButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        // 添加按钮点击事件监听器
        refreshButton.addActionListener(e -> {
            // 手动刷新统计卡片和时间标签
            refreshStatsCards();
            updateTimeLabel();
        });
        
        buttonPanel.add(refreshButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);

        // 统计卡片面板优化
        this.statsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        statsPanel.setBackground(new java.awt.Color(249, 249, 249)); // 浅灰背景

        // 添加统计卡片（使用新的配色方案）
        refreshStatsCards();

        add(statsPanel, BorderLayout.CENTER);

        // 底部信息优化
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 15));
        footerPanel.setBackground(new java.awt.Color(245, 245, 245));
        this.timeLabel = new JLabel("数据更新时间: " +
                java.time.LocalDateTime.now().toString().substring(0, 16));
        timeLabel.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 12));
        timeLabel.setForeground(new java.awt.Color(102, 102, 102));
        footerPanel.add(timeLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }

    // 修改createStatCard方法，支持显示细分数据
    private JPanel createStatCard(String title, String value, java.awt.Color valueColor, java.awt.Color bgColor, List<String[]> details, boolean isDepartmentDetails) {
        JPanel card = new JPanel(new BorderLayout());
        // 卡片阴影和边框效果
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(bgColor); // 卡片背景色
        
        // 计算合适的卡片高度，确保所有细分内容都能显示
        int baseHeight = 150; // 基础高度
        int detailHeight = (details != null && !details.isEmpty()) ? (details.size() * 25 + 20) : 0; // 每个明细项25像素，额外20像素间距
        card.setPreferredSize(new Dimension(0, baseHeight + detailHeight));

        // 标题
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 16));
        titleLabel.setForeground(new java.awt.Color(102, 102, 102));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        card.add(titleLabel, BorderLayout.NORTH);

        // 主数值
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 28));
        valueLabel.setForeground(valueColor);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        card.add(valueLabel, BorderLayout.CENTER);
        
        // 细分内容
        if (details != null && !details.isEmpty()) {
            JPanel detailPanel = new JPanel(new GridLayout(details.size(), 1, 0, 5));
            detailPanel.setBackground(bgColor); // 设置与卡片相同的背景色
            
            for (String[] detail : details) {
                JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                rowPanel.setBackground(bgColor); // 设置与卡片相同的背景色
                
                JLabel detailLabel;
                if (isDepartmentDetails) {
                    // 部门明细：部门名称(编码) - 人数
                    String deptText = detail[1] + "(" + detail[0] + "): " + detail[2] + "人";
                    detailLabel = new JLabel(deptText);
                } else {
                    // 变动类型明细：类型 - 数量
                    String changeText = detail[0] + ": " + detail[1] + "条";
                    detailLabel = new JLabel(changeText);
                }
                detailLabel.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 12));
                detailLabel.setForeground(new java.awt.Color(128, 128, 128));
                rowPanel.add(detailLabel);
                detailPanel.add(rowPanel);
            }
            
            // 移除边框，让细分内容自然融入板块
            detailPanel.setBorder(null);
            card.add(detailPanel, BorderLayout.SOUTH);
        }

        return card;
    }
    
    // 重载方法，用于不需要细分内容的卡片
    private JPanel createStatCard(String title, String value, java.awt.Color valueColor, java.awt.Color bgColor) {
        return createStatCard(title, value, valueColor, bgColor, null, false);
    }

    /**
     * 创建统计卡片
     */
    private JPanel createStatCard(String title, String value, java.awt.Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY));
        card.setBackground(java.awt.Color.WHITE);

        // 标题
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        card.add(titleLabel, BorderLayout.NORTH);

        // 数值
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 24));
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
    
    // 获取各部门员工数
    private List<String[]> getDepartmentEmployeeCounts() {
        List<String[]> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT d.id, d.name, COUNT(p.id) AS emp_count " +
                     "FROM department d " +
                     "LEFT JOIN person p ON d.id = p.department " +
                     "GROUP BY d.id, d.name " +
                     "ORDER BY d.id";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                // 格式化部门ID为两位数字显示
                String code = String.format("%02d", rs.getInt("id"));
                String name = rs.getString("name").trim(); // 去除可能的空格
                int count = rs.getInt("emp_count");
                result.add(new String[]{code, name, String.valueOf(count)});
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取各部门员工数出错: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt, conn);
        }
        return result;
    }
    
    // 获取各部门在职员工数
    private List<String[]> getDepartmentActiveEmployeeCounts() {
        List<String[]> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT d.id, d.name, COUNT(p.id) AS active_count " +
                     "FROM department d " +
                     "LEFT JOIN person p ON d.id = p.department AND p.state = 't' " +
                     "GROUP BY d.id, d.name " +
                     "ORDER BY d.id";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                // 格式化部门ID为两位数字显示
                String code = String.format("%02d", rs.getInt("id"));
                String name = rs.getString("name").trim(); // 去除可能的空格
                int count = rs.getInt("active_count");
                result.add(new String[]{code, name, String.valueOf(count)});
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取各部门在职员工数出错: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt, conn);
        }
        return result;
    }
    
    // 获取各变动类型数目
    private List<String[]> getPersonnelChangeTypeCounts() {
        List<String[]> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // 正确的SQL查询应该连接personnel和personnel_change表
        String sql = "SELECT pc.description, COUNT(*) AS type_count " +
                     "FROM personnel p " +
                     "LEFT JOIN personnel_change pc ON p.`change` = pc.code " +
                     "GROUP BY pc.description " +
                     "ORDER BY pc.description";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String typeName = rs.getString("description");
                int count = rs.getInt("type_count");
                result.add(new String[]{typeName, String.valueOf(count)});
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取各变动类型数目出错: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt, conn);
        }
        return result;
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
    
    /**
     * 开始自动刷新功能
     */
    private void startAutoRefresh() {
        // 创建定时器，每30秒刷新一次
        autoRefreshTimer = new Timer();
        autoRefreshTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    refreshStatsCards();
                    updateTimeLabel();
                });
            }
        }, 30000, 30000); // 30秒后开始，每30秒执行一次
    }
    
    /**
     * 刷新统计卡片
     */
    private void refreshStatsCards() {
        // 清空现有卡片
        statsPanel.removeAll();
        
        // 获取各板块的细分数据
        List<String[]> deptEmployeeCounts = getDepartmentEmployeeCounts();
        List<String[]> deptActiveCounts = getDepartmentActiveEmployeeCounts();
        List<String[]> changeTypeCounts = getPersonnelChangeTypeCounts();
        
        // 重新添加统计卡片
        statsPanel.add(createStatCard("总员工数", getTotalEmployeeCount() + " 人",
                new java.awt.Color(76, 175, 80), new java.awt.Color(232, 245, 233), deptEmployeeCounts, true));
        statsPanel.add(createStatCard("部门总数", getDepartmentCount() + " 个",
                new java.awt.Color(33, 150, 243), new java.awt.Color(227, 242, 253)));
        statsPanel.add(createStatCard("在职员工", getActiveEmployeeCount() + " 人",
                new java.awt.Color(255, 152, 0), new java.awt.Color(255, 248, 225), deptActiveCounts, true));
        statsPanel.add(createStatCard("人事变动", getPersonnelChanges() + " 条",
                new java.awt.Color(244, 67, 54), new java.awt.Color(255, 235, 238), changeTypeCounts, false));
        
        // 重新布局面板
        statsPanel.revalidate();
        statsPanel.repaint();
    }
    
    /**
     * 更新时间标签
     */
    private void updateTimeLabel() {
        timeLabel.setText("数据更新时间: " +
                java.time.LocalDateTime.now().toString().substring(0, 16));
    }
    

    
    /**
     * 停止自动刷新（清理资源）
     */
    public void stopAutoRefresh() {
        if (autoRefreshTimer != null) {
            autoRefreshTimer.cancel();
            autoRefreshTimer = null;
        }
    }
    
}