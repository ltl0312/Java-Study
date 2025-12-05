// pms/Service/src/com/pms/service/DepartmentService.java
package com.pms.service;

import com.pms.model.Department;
import javax.swing.JOptionPane;
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
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            // 先检查ID是否已存在
            if (getDepartmentById(department.getId()) != null) {
                JOptionPane.showMessageDialog(null, "部门ID已存在，添加失败", "错误", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            String sql = "INSERT INTO department (id, name, manager, intro) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, department.getId());
            pstmt.setString(2, department.getName());
            pstmt.setString(3, department.getManager());
            pstmt.setString(4, department.getIntro());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "添加失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            closeResources(conn, pstmt, null);
        }
    }

    /**
     * 更新部门
     */
    public boolean updateDepartment(Department department) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            String sql = "UPDATE department SET name=?, manager=?, intro=? WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, department.getName());
            pstmt.setString(2, department.getManager());
            pstmt.setString(3, department.getIntro());
            pstmt.setInt(4, department.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "更新失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            closeResources(conn, pstmt, null);
        }
    }

    /**
     * 删除部门
     */
    public boolean deleteDepartment(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement checkStmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            // 先检查是否有关联员工
            String checkSql = "SELECT COUNT(*) FROM person WHERE department=?";
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, id);
            rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "该部门下有关联员工，无法删除", "错误", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // 执行删除
            String sql = "DELETE FROM department WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "删除失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            closeResources(conn, pstmt, rs);
            closeResources(null, checkStmt, null); // 关闭检查用的Statement
        }
    }

    /**
     * 根据ID查询部门（核心修复：移除所有递归调用）
     */
    public Department getDepartmentById(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String sql = "SELECT id, name, manager, intro FROM department WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id); // 绑定查询参数
            rs = pstmt.executeQuery();

            // 仅当查询到结果时才创建Department对象，无递归！
            if (rs.next()) {
                Department dept = new Department();
                dept.setId(rs.getInt("id"));
                dept.setName(rs.getString("name"));
                dept.setManager(rs.getString("manager"));
                dept.setIntro(rs.getString("intro"));
                return dept; // 返回查询结果，无自调用
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "查询部门失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        } finally {
            closeResources(conn, pstmt, rs); // 确保资源释放
        }
        return null; // 未查询到返回null
    }
}