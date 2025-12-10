package com.pms.utils;

import com.pms.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResetPersonnelChanges {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            // 获取数据库连接
            conn = DBConnection.getConnection();
            
            // 1. 删除所有人事变动记录
            String deleteSql = "DELETE FROM personnel";
            pstmt = conn.prepareStatement(deleteSql);
            int deletedRows = pstmt.executeUpdate();
            System.out.println("已删除 " + deletedRows + " 条人事变动记录");
            pstmt.close();
            
            // 2. 重置自动递增的ID
            String resetSql = "ALTER TABLE personnel AUTO_INCREMENT = 1";
            pstmt = conn.prepareStatement(resetSql);
            pstmt.executeUpdate();
            System.out.println("人事变动记录ID已重置为1");
            
            System.out.println("操作完成！");
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}