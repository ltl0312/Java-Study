package com.pms.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResetPersonnelTable {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;

        try {
            // 获取数据库连接
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // 开启事务

            // 1. 删除所有人事变动记录
            String deleteSql = "DELETE FROM personnel";
            pstmt1 = conn.prepareStatement(deleteSql);
            int deletedCount = pstmt1.executeUpdate();
            System.out.println("已删除 " + deletedCount + " 条人事变动记录");

            // 2. 重置ID自增
            String resetSql = "ALTER TABLE personnel AUTO_INCREMENT = 1";
            pstmt2 = conn.prepareStatement(resetSql);
            pstmt2.executeUpdate();
            System.out.println("已重置人事变动记录ID自增，从1开始");

            conn.commit(); // 提交事务
            System.out.println("操作成功完成");

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // 回滚事务
                    System.out.println("操作失败，已回滚");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}