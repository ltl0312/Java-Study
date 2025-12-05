// pms/Service/src/com/pms/service/PersonnelChangeService.java
package com.pms.service;

import com.pms.model.PersonnelChange;
import com.pms.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                change.setChangeTime(rs.getTimestamp("change_time"));
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
        // 1. 前置校验：确保核心字段非空
        if (change == null) {
            System.err.println("变动记录对象为null！");
            return false;
        }
        if (change.getChangeType() == null || change.getChangeType().trim().isEmpty()) {
            System.err.println("变动类型不能为空！");
            return false;
        }
        if (change.getEmployeeId() <= 0) {
            System.err.println("员工ID无效！");
            return false;
        }

        // 2. 调用getChangeCode（此时已确保changeType非空）
        int changeCode = getChangeCode(change.getChangeType());
        String sql = "INSERT INTO personnel " +  // 修正表名
                "(person, employee_name, `change`, change_type, description, change_time) " +  // 调整字段名匹配personnel表
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, change.getEmployeeId());  // 对应person字段（员工ID）
            pstmt.setString(2, change.getEmployeeName());
            pstmt.setInt(3, changeCode);  // 对应change字段（变动类型编码）
            pstmt.setString(4, change.getChangeType());
            pstmt.setString(5, change.getDescription());
            pstmt.setTimestamp(6, change.getChangeTime());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据变动类型描述获取对应的code值
     */
    private int getChangeCode(String changeType) {
        // 1. 空值校验：若changeType为null，直接返回默认值（或抛明确异常）
        if (changeType == null || changeType.trim().isEmpty()) {
            throw new IllegalArgumentException("人事变动类型不能为空！");
            // 或兜底返回默认值：return -1;（根据业务选择）
        }

        // 2. 初始化变动类型与编码的映射（确保key非空）
        Map<String, Integer> typeMap = new HashMap<>();
        typeMap.put("职务变动", 2);
        typeMap.put("部门变动", 3);
        typeMap.put("辞退", 4);
        typeMap.put("新员工加入", 1); // 补充所有变动类型

        // 3. 若未匹配到类型，返回兜底值（避免返回null）
        Integer code = typeMap.get(changeType.trim());
        if (code == null) {
            throw new IllegalArgumentException("不支持的变动类型：" + changeType);
            // 或兜底：return 0;
        }
        return code;
    }

    /**
     * 根据员工ID获取其变动记录
     */
    public List<PersonnelChange> getChangesByEmployeeId(int employeeId) {
        List<PersonnelChange> changes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String sql = "SELECT p.id, p.person, pe.name, pc.description, p.description, p.change_time " +
                    "FROM personnel p " +
                    "LEFT JOIN person pe ON p.person = pe.id " +
                    "LEFT JOIN personnel_change pc ON p.`change` = pc.code " +
                    "WHERE p.person = ? " +
                    "ORDER BY p.change_time DESC";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, employeeId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                PersonnelChange change = new PersonnelChange();
                change.setId(rs.getInt(1));
                change.setEmployeeId(rs.getInt(2));
                change.setEmployeeName(rs.getString(3));
                change.setChangeType(rs.getString(4));
                change.setDescription(rs.getString(5));
                change.setChangeTime(rs.getTimestamp(6));
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
     * 删除人事变动记录
     */
    public boolean deleteChange(int changeId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            String sql = "DELETE FROM personnel WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, changeId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, pstmt, null);
        }
    }
}