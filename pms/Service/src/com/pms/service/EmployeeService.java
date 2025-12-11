package com.pms.service;

import com.pms.model.Employee;

import com.pms.model.PersonnelChange;
import com.pms.utils.DBConnection;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService extends BaseService {
    /**
     * 添加员工（使用int类型的部门/职位/学历代码）
     */
    public boolean addEmployee(Employee employee) {
        // 新增验证
        if (!isValidJobCode(employee.getJobCode())) {
            System.err.println("无效的职位代码: " + employee.getJobCode());
            return false;
        }

        boolean success = false;
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
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        // 人事变动记录已在PersonnelChangeDialog中保存，这里不再重复保存
        
        return success;
    }

    // 删除员工
    public boolean deleteEmployee(int employeeId) {
        boolean success = false;
        String employeeName = "";
        
        try {
            // 1. 先获取员工姓名
            String sql1 = "SELECT name FROM person WHERE id = ?";
            try (Connection conn1 = DBConnection.getConnection();
                 PreparedStatement pstmt1 = conn1.prepareStatement(sql1)) {
                pstmt1.setInt(1, employeeId);
                try (ResultSet rs = pstmt1.executeQuery()) {
                    if (rs.next()) {
                        employeeName = rs.getString("name");
                    } else {
                        System.out.println("员工不存在，ID：" + employeeId);
                        return false;
                    }
                }
            }
            
            // 2. 先删除personnel表中相关的人事变动记录
            String sqlDeletePersonnel = "DELETE FROM personnel WHERE person_id = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sqlDeletePersonnel)) {
                pstmt.setInt(1, employeeId);
                pstmt.executeUpdate();
            }
            
            // 3. 执行删除员工操作
            String sql = "DELETE FROM person WHERE id = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, employeeId);
                int rows = pstmt.executeUpdate();
                success = (rows > 0);
            }
            
            // 3. 删除成功后记录人事变动（非事务性，即使记录失败也不影响删除）
            if (success) {
                PersonnelChange change = new PersonnelChange();
                change.setEmployeeId(employeeId);
                change.setEmployeeName(employeeName);
                change.setChangeType("辞退");
                change.setDescription("员工被辞退");
                change.setChangeTime(new Timestamp(new Date().getTime()));

                PersonnelChangeService changeService = new PersonnelChangeService();
                try {
                    changeService.addChange(change);
                } catch (Exception e) {
                    System.out.println("记录人事变动失败，但员工删除已成功：" + e.getMessage());
                    // 不影响删除结果，继续返回成功
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error in deleteEmployee: " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }

    // 其他方法（updateEmployee等）...
    public boolean updateEmployee(Employee employee) {
        // 新增验证
        if (!isValidJobCode(employee.getJobCode())) {
            System.err.println("无效的职位代码: " + employee.getJobCode());
            return false;
        }

        // 1. 查询旧部门ID和旧职位信息（用于对比是否变动）
        int oldDeptId = 0;
        String oldJob = "";
        String sqlOldInfo = "SELECT department, job, (SELECT description FROM job WHERE code = p.job) as job_name " +
                "FROM person p WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmtOld = conn.prepareStatement(sqlOldInfo)) {
            pstmtOld.setInt(1, employee.getId());
            ResultSet rsOld = pstmtOld.executeQuery();
            if (rsOld.next()) {
                oldDeptId = rsOld.getInt("department");
                oldJob = rsOld.getString("job_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 2. 执行员工信息更新（原有逻辑）
        boolean success = false;
        String sql = "UPDATE person SET " +
                "name = ?, sex = ?, birthday = ?, department = ?, job = ?, edu_level = ?, " +
                "specialty = ?, address = ?, tel = ?, email = ?, remark = ?, state = ? " +
                "WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getSex());
            pstmt.setDate(3, new java.sql.Date(employee.getBirthday().getTime()));
            pstmt.setInt(4, employee.getDepartmentId()); // 部门ID
            pstmt.setInt(5, employee.getJobCode());      // 职位代码
            pstmt.setInt(6, employee.getEduLevelCode()); // 学历代码
            pstmt.setString(7, employee.getSpecialty());
            pstmt.setString(8, employee.getAddress());
            pstmt.setString(9, employee.getTel());
            pstmt.setString(10, employee.getEmail());
            pstmt.setString(11, employee.getRemark());
            pstmt.setString(12, String.valueOf(employee.getState()));    // 员工状态（如"t"在职/"f"离职）
            pstmt.setInt(13, employee.getId());          // WHERE条件的员工ID
            pstmt.executeUpdate();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // 3. 若更新成功，记录变动（职位/部门）
        // 人事变动记录已在PersonnelChangeDialog中保存，这里不再重复保存
        
        return success;
    }

    // 辅助方法：根据部门ID获取部门名称
    private String getDeptName(int deptId) {
        String deptName = "";
        String sql = "SELECT name FROM department WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, deptId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                deptName = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deptName;
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
            // 假设有效的职位代码为正整数（根据实际数据库设计调整范围）
            if (jobCode <= 0) {
                System.out.println("无效的职位代码: " + jobCode);
                // 可选择抛出异常或忽略该条件
                // throw new IllegalArgumentException("职位代码必须为正整数");
            } else {
                sql.append("AND p.job = ? ");
                params.add(jobCode);
            }
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

    // 添加验证方法
    private boolean isValidJobCode(int jobCode) {
        // 根据数据库中job表的code范围（1-15）校验
        boolean valid = jobCode >= 1 && jobCode <= 15;
        if (!valid) {
            System.err.println("无效的职位代码: " + jobCode + "（有效范围：1-15）");
        }
        return valid;
    }
    
    /**
     * 根据部门编号自动生成员工ID
     * 生成规则：8位数字，前四位固定为公司代码6504，中间两位为部门ID，后两位为部门内员工编号
     * 例如：技术部(1)的员工ID为65040101, 65040102, 65040103...
     * 人事部(2)的员工ID为65040201, 65040202, 65040203...
     * 注意：需要检查所有部门的员工ID以确保唯一性，因为员工变动部门时ID会保留
     */
    public int generateEmployeeIdByDepartmentId(int departmentId) {
        // 1. 构建部门ID的前缀（固定为两位数字，不足两位前面补0）
        String deptPrefix = String.format("%02d", departmentId);
        
        // 2. 查询该部门中所有符合新规则（前四位为6504）的员工ID
        String sqlDept = "SELECT id FROM person WHERE department = ? AND id LIKE '6504%' ORDER BY id DESC";
        List<Integer> deptIds = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlDept)) {
            
            pstmt.setInt(1, departmentId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                deptIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // 3. 提取出部门内的最大编号
        int maxDeptSeq = 0;
        for (int id : deptIds) {
            String idStr = String.valueOf(id);
            // 确保ID是8位且前四位是6504
            if (idStr.length() == 8 && idStr.startsWith("6504")) {
                // 提取部门ID部分和序号部分
                String idDept = idStr.substring(4, 6);
                if (idDept.equals(deptPrefix)) {
                    // 提取后两位序号
                    int seq = Integer.parseInt(idStr.substring(6, 8));
                    if (seq > maxDeptSeq) {
                        maxDeptSeq = seq;
                    }
                }
            }
        }
        
        // 4. 生成新的员工ID
        int newSeq = maxDeptSeq + 1;
        int newId;
        boolean idExists;
        
        // 使用循环代替递归检查ID是否存在
        do {
            String newIdStr = "6504" + deptPrefix + String.format("%02d", newSeq);
            newId = Integer.parseInt(newIdStr);
            
            // 检查生成的ID是否已存在于系统中（防止部门变动导致的ID重复）
            String checkSql = "SELECT COUNT(*) FROM person WHERE id = ?";
            idExists = false;
            
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(checkSql)) {
                
                pstmt.setInt(1, newId);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next() && rs.getInt(1) > 0) {
                    idExists = true;
                    newSeq++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                idExists = false; // 如果数据库查询出错，假设ID可用
            }
        } while (idExists);
        
        return newId;
    }

}