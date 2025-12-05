// pms/Service/src/com/pms/service/BaseService.java
package com.pms.service;

import com.pms.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 基础服务类，提供数据库操作的通用方法
 */
public class BaseService {
    /**
     * 获取数据库连接
     */
    protected Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }
    
    /**
     * 关闭数据库资源
     */
    protected void closeResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}