// pms/Service/src/com/pms/service/DepartmentService.java
package com.pms.service;

import com.pms.model.Department;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentService extends BaseService {
    
    /**
     * 获取所有部门
     */
    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            String sql = "SELECT id, name, manager, intro FROM department";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Department dept = new Department();
                dept.setId(rs.getInt("id"));
                dept.setName(rs.getString("name"));
                dept.setManager(rs.getString("manager"));
                dept.setIntro(rs.getString("intro"));
                departments.add(dept);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }
        return departments;
    }
    
    /**
     * 添加部门
     */
    public boolean addDepartment(Department department) {
        // 实现添加逻辑
        return false;
    }
    
    /**
     * 更新部门
     */
    public boolean updateDepartment(Department department) {
        // 实现更新逻辑
        return false;
    }
    
    /**
     * 删除部门
     */
    public boolean deleteDepartment(int id) {
        // 实现删除逻辑
        return false;
    }
}