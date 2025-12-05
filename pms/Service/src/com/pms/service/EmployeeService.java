package com.pms.service;

import com.pms.model.Employee;
import com.pms.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    /**
     * 添加员工（使用int类型的部门/职位/学历代码）
     */
    public boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO person (" +
                "id, password, authority, name, sex, birthday, " +
                "department, job, edu_level, specialty, address, tel, email, state, remark" +
                ") VALUES (?, ?, 'staff', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 't', ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, employee.getId());
            pstmt.setString(2, employee.getPassword() != null ? employee.getPassword() : "123456"); // 默认密码
            pstmt.setString(3, employee.getName());
            pstmt.setString(4, employee.getSex());
            pstmt.setDate(5, new java.sql.Date(employee.getBirthday().getTime()));
            // 核心：使用int类型代码
            pstmt.setInt(6, employee.getDepartmentId()); // 部门代码
            pstmt.setInt(7, employee.getJobCode());      // 职位代码
            pstmt.setInt(8, employee.getEduLevelCode()); // 学历代码
            pstmt.setString(9, employee.getSpecialty());
            pstmt.setString(10, employee.getAddress());
            pstmt.setString(11, employee.getTel());
            pstmt.setString(12, employee.getEmail());
            pstmt.setString(13, employee.getRemark());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 删除员工（待实现）
    public boolean deleteEmployee(int employeeId) {
        // 实现删除逻辑
        String sql = "DELETE FROM person WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // 其他方法（updateEmployee等）...
    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE person SET " +
                "name = ?, sex = ?, birthday = ?, department = ?, job = ?, edu_level = ?, " +
                "specialty = ?, address = ?, tel = ?, email = ?, remark = ?, state = ? " +
                "WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getSex());
            pstmt.setDate(3, employee.getBirthday() != null ?
                    new java.sql.Date(employee.getBirthday().getTime()) : null);
            pstmt.setInt(4, employee.getDepartmentId());
            pstmt.setInt(5, employee.getJobCode());
            pstmt.setInt(6, employee.getEduLevelCode());
            pstmt.setString(7, employee.getSpecialty());
            pstmt.setString(8, employee.getAddress());
            pstmt.setString(9, employee.getTel());
            pstmt.setString(10, employee.getEmail());
            pstmt.setString(11, employee.getRemark());
            pstmt.setString(12, String.valueOf(employee.getState()));
            pstmt.setInt(13, employee.getId());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 在 EmployeeService.java 中添加
    public List<Employee> getEmployeesByCondition(String keyword, int deptId, int jobCode,
                                                  String state, int offset, int pageSize) {
        List<Employee> employees = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.*, d.name as dept_name, j.description as job_name ");
        sql.append("FROM person p ");
        sql.append("LEFT JOIN department d ON p.department = d.id ");
        sql.append("LEFT JOIN job j ON p.job = j.code ");
        sql.append("WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        // 添加搜索条件
        if (keyword != null && !keyword.isEmpty()) {
            sql.append("AND (p.id LIKE ? OR p.name LIKE ? OR p.sex LIKE ? ");
            sql.append("OR p.tel LIKE ? OR p.email LIKE ? OR p.address LIKE ? ");
            sql.append("OR d.name LIKE ? OR j.description LIKE ?) ");
            String likeKeyword = "%" + keyword + "%";
            for (int i = 0; i < 8; i++) {
                params.add(likeKeyword);
            }
        }

        // 部门筛选
        if (deptId != -1) {
            sql.append("AND p.department = ? ");
            params.add(deptId);
        }

        // 职位筛选
        if (jobCode != -1) {
            sql.append("AND p.job = ? ");
            params.add(jobCode);
        }

        // 状态筛选
        if (state != null && !state.isEmpty()) {
            sql.append("AND p.state = ? ");
            params.add(state);
        }

        // 分页
        sql.append("LIMIT ? OFFSET ?");
        params.add(pageSize);
        params.add(offset);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setName(rs.getString("name"));
                emp.setSex(rs.getString("sex"));
                emp.setDepartmentId(rs.getInt("department"));
                emp.setDepartmentName(rs.getString("dept_name"));
                emp.setJobCode(rs.getInt("job"));
                emp.setJobName(rs.getString("job_name"));
                emp.setTel(rs.getString("tel"));
                emp.setState(rs.getString("state").charAt(0));
                // 设置其他需要的字段
                employees.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }


    // 修改EmployeeService中的getEmployeesByCondition方法，添加排序参数
    public List<Employee> getEmployeesByCondition(String keyword, int deptId, int jobCode,
                                                  String state, int offset, int pageSize,
                                                  String sortField, String sortDirection) {
        List<Employee> employees = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.*, d.name as dept_name, j.description as job_name ");
        sql.append("FROM person p ");
        sql.append("LEFT JOIN department d ON p.department = d.id ");
        sql.append("LEFT JOIN job j ON p.job = j.code ");
        sql.append("WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        // 添加搜索条件（保持不变）
        if (keyword != null && !keyword.isEmpty()) {
            sql.append("AND (p.id LIKE ? OR p.name LIKE ? OR p.sex LIKE ? ");
            sql.append("OR p.tel LIKE ? OR p.email LIKE ? OR p.address LIKE ? ");
            sql.append("OR d.name LIKE ? OR j.description LIKE ?) ");
            String likeKeyword = "%" + keyword + "%";
            for (int i = 0; i < 8; i++) {
                params.add(likeKeyword);
            }
        }

        // 部门筛选（保持不变）
        if (deptId != -1) {
            sql.append("AND p.department = ? ");
            params.add(deptId);
        }

        // 职位筛选（保持不变）
        if (jobCode != -1) {
            sql.append("AND p.job = ? ");
            params.add(jobCode);
        }

        // 状态筛选（保持不变）
        if (state != null && !state.isEmpty() && !"全部".equals(state)) {
            sql.append("AND p.state = ? ");
            params.add("在职".equals(state) ? "t" : "f");
        }

        // 添加排序条件
        sql.append("ORDER BY ").append(sortField).append(" ").append(sortDirection).append(" ");

        // 分页（保持不变）
        sql.append("LIMIT ? OFFSET ?");
        params.add(pageSize);
        params.add(offset);

        // 执行查询（保持不变）
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setName(rs.getString("name"));
                emp.setSex(rs.getString("sex"));
                emp.setDepartmentId(rs.getInt("department"));
                emp.setDepartmentName(rs.getString("dept_name"));
                emp.setJobCode(rs.getInt("job"));
                emp.setJobName(rs.getString("job_name"));
                emp.setTel(rs.getString("tel"));
                emp.setState(rs.getString("state").charAt(0));
                employees.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // 在 EmployeeService.java 中添加如下方法
    /**
     * 根据条件查询员工总记录数（修复表名错误）
     */
    public int getEmployeeCountByCondition(String keyword, int deptId, int jobCode, String state) {
        int count = 0;
        StringBuilder sql = new StringBuilder();
        // 关键修复：表名改为person（原错误可能写了employee），并保持关联表别名一致
        sql.append("SELECT COUNT(*) FROM person p ");
        sql.append("LEFT JOIN department d ON p.department = d.id ");
        sql.append("LEFT JOIN job j ON p.job = j.code ");
        sql.append("WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        // 搜索条件（与查询数据的逻辑保持一致）
        if (keyword != null && !keyword.isEmpty()) {
            sql.append("AND (p.id LIKE ? OR p.name LIKE ? OR p.sex LIKE ? ");
            sql.append("OR p.tel LIKE ? OR p.email LIKE ? OR p.address LIKE ? ");
            sql.append("OR d.name LIKE ? OR j.description LIKE ?) ");
            String likeKeyword = "%" + keyword + "%";
            for (int i = 0; i < 8; i++) {
                params.add(likeKeyword);
            }
        }

        // 部门筛选
        if (deptId != -1) {
            sql.append("AND p.department = ? ");
            params.add(deptId);
        }

        // 职位筛选
        if (jobCode != -1) {
            sql.append("AND p.job = ? ");
            params.add(jobCode);
        }

        // 状态筛选（匹配person表的state字段）
        if (state != null && !state.isEmpty() && !"全部".equals(state)) {
            sql.append("AND p.state = ? ");
            // 注意：数据库中state字段是char类型（t=在职，f=离职），需转换
            params.add("在职".equals(state) ? "t" : "f");
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            // 绑定参数
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("查询员工总数失败：" + e.getMessage());
        }
        return count;
    }


    public Employee getEmployeeById(int employeeId) {
        Employee emp = null;
        String sql = "SELECT p.*, d.name as dept_name, j.description as job_name " +
                "FROM person p " +
                "LEFT JOIN department d ON p.department = d.id " +
                "LEFT JOIN job j ON p.job = j.code " +
                "WHERE p.id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setName(rs.getString("name"));
                emp.setPassword(rs.getString("password"));
                emp.setAuthority(rs.getString("authority"));
                emp.setSex(rs.getString("sex"));
                emp.setBirthday(rs.getDate("birthday"));
                emp.setAddress(rs.getString("address"));
                emp.setTel(rs.getString("tel"));
                emp.setEmail(rs.getString("email"));
                emp.setRemark(rs.getString("remark"));
                emp.setDepartmentId(rs.getInt("department"));
                emp.setDepartmentName(rs.getString("dept_name"));
                emp.setJobCode(rs.getInt("job"));
                emp.setJobName(rs.getString("job_name"));
                emp.setEduLevelCode(rs.getInt("edu_level"));
                emp.setSpecialty(rs.getString("specialty"));
                emp.setState(rs.getString("state").charAt(0));
                emp.setAuthority(rs.getString("authority"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emp;
    }

    // 获取员工数量
    public int getEmployeeCount(String keyword, int deptId, int jobCode, String state) {
        int count = 0;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM person p ");
        sql.append("LEFT JOIN department d ON p.department = d.id ");
        sql.append("LEFT JOIN job j ON p.job = j.code ");
        sql.append("WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        // 添加搜索条件（与getEmployeesByCondition方法相同）
        if (keyword != null && !keyword.isEmpty()) {
            sql.append("AND (p.id LIKE ? OR p.name LIKE ? OR p.sex LIKE ? ");
            sql.append("OR p.tel LIKE ? OR p.email LIKE ? OR p.address LIKE ? ");
            sql.append("OR d.name LIKE ? OR j.description LIKE ?) ");
            String likeKeyword = "%" + keyword + "%";
            for (int i = 0; i < 8; i++) {
                params.add(likeKeyword);
            }
        }

        if (deptId != -1) {
            sql.append("AND p.department = ? ");
            params.add(deptId);
        }

        if (jobCode != -1) {
            sql.append("AND p.job = ? ");
            params.add(jobCode);
        }

        if (state != null && !state.isEmpty()) {
            sql.append("AND p.state = ? ");
            params.add(state);
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}