// pms/Service/src/com/pms/service/PersonnelChangeService.java
package com.pms.service;

import com.pms.model.PersonnelChange;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonnelChangeService extends BaseService {
    
    /**
     * 获取所有人事变动记录
     */
    public List<PersonnelChange> getAllChanges() {
        List<PersonnelChange> changes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            String sql = "SELECT id, employee_id, employee_name, change_type, description, change_time " +
                       "FROM personnel_change ORDER BY change_time DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                PersonnelChange change = new PersonnelChange();
                change.setId(rs.getInt("id"));
                change.setEmployeeId(rs.getInt("employee_id"));
                change.setEmployeeName(rs.getString("employee_name"));
                change.setChangeType(rs.getString("change_type"));
                change.setDescription(rs.getString("description"));
                change.setChangeTime(rs.getString("change_time"));
                changes.add(change);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }
        return changes;
    }
    
    /**
     * 添加人事变动记录
     */
    public boolean addChange(PersonnelChange change) {
        // 实现添加逻辑
        return false;
    }
}