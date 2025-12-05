package com.pms.utils;

import java.sql.*;

public class DBConnection {
    // 数据库连接信息（使用实际密码替换）
    private static final String URL = "jdbc:mysql://localhost:3306/pms?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "a82711045"; // 替换为实际数据库密码（与静态块一致）

    static {
        try {
            // 加载MySQL 8.0+推荐的驱动类（无需手动注册，SPI会自动处理）
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 测试连接（使用pms数据库，与URL保持一致）
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("MySQL驱动加载成功");
            //conn.close(); // 测试后关闭连接
        } catch (Exception e) {
            System.err.println("MySQL驱动加载失败: " + e.getMessage());
            e.printStackTrace();

        }
    }

    /**
     * 获取数据库连接
     * @return 数据库连接对象
     * @throws SQLException SQL异常
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * 关闭数据库连接
     * @param conn 数据库连接对象
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closePreparedStatement(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}