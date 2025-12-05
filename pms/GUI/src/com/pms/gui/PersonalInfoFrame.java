package com.pms.gui;

import com.pms.model.Employee;
import com.pms.utils.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonalInfoFrame extends JFrame {
    private int employeeId;
    private Employee employee;

    // 可编辑字段（全部初始化，避免null）
    private JPasswordField passwordField = new JPasswordField(20);
    private JComboBox<String> sexCombo = new JComboBox<>(new String[]{"男", "女"});
    private JTextField birthdayField = new JTextField(20);
    private JTextField addressField = new JTextField(20);
    private JTextField telField = new JTextField(20);
    private JTextField emailField = new JTextField(20);
    private JTextArea remarkArea = new JTextArea(3, 20);

    // 不可编辑字段（全部初始化，避免null）
    private JTextField idField = new JTextField(20);
    private JTextField nameField = new JTextField(20);
    private JTextField departmentField = new JTextField(20);
    private JTextField jobField = new JTextField(20);
    private JTextField superiorField = new JTextField(20); // 关键：之前可能未初始化

    public PersonalInfoFrame(int employeeId) {
        this.employeeId = employeeId;
        // 修复：查询失败时返回空Employee，避免null
        this.employee = loadEmployeeInfo() == null ? new Employee() : loadEmployeeInfo();
        initUI();
        setTitle("个人信息管理");
        setSize(600, 600); // 调整大小，避免组件挤压
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false); // 固定窗口大小，提升稳定性
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 添边距

        // 顶部信息
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(new JLabel("欢迎，" + (employee.getName() == null ? "未知用户" : employee.getName())));
        JButton logoutBtn = new JButton("退出登录");
        logoutBtn.addActionListener(e -> logout());
        topPanel.add(logoutBtn);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 个人信息面板（修复GridBagLayout使用方式，避免组件null）
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL; // 组件水平填充
        gbc.gridx = 0;
        gbc.gridy = 0;

        // 加载信息到表单（确保组件都已初始化）
        loadInfoToForm();

        // ========== 逐个添加组件（确保无null组件） ==========
        // 员工ID（不可编辑）
        infoPanel.add(new JLabel("员工ID:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(idField, gbc);

        // 姓名（不可编辑）
        gbc.gridx = 0;
        gbc.gridy++;
        infoPanel.add(new JLabel("姓名:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(nameField, gbc);

        // 所属部门（不可编辑）
        gbc.gridx = 0;
        gbc.gridy++;
        infoPanel.add(new JLabel("所属部门:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(departmentField, gbc);

        // 职位（不可编辑）
        gbc.gridx = 0;
        gbc.gridy++;
        infoPanel.add(new JLabel("职位:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(jobField, gbc);

//        // 上级（不可编辑）
//        gbc.gridx = 0;
//        gbc.gridy++;
//        infoPanel.add(new JLabel("直属上级:"), gbc);
//        gbc.gridx = 1;
//        infoPanel.add(superiorField, gbc);

        // 密码（可编辑）
        gbc.gridx = 0;
        gbc.gridy++;
        infoPanel.add(new JLabel("密码:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(passwordField, gbc);

        // 性别（可编辑）
        gbc.gridx = 0;
        gbc.gridy++;
        infoPanel.add(new JLabel("性别:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(sexCombo, gbc);

        // 出生日期（可编辑）
        gbc.gridx = 0;
        gbc.gridy++;
        infoPanel.add(new JLabel("出生日期(yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        infoPanel.add(birthdayField, gbc);

        // 地址（可编辑）
        gbc.gridx = 0;
        gbc.gridy++;
        infoPanel.add(new JLabel("地址:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(addressField, gbc);

        // 电话（可编辑）
        gbc.gridx = 0;
        gbc.gridy++;
        infoPanel.add(new JLabel("联系电话:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(telField, gbc);

        // 邮箱（可编辑）
        gbc.gridx = 0;
        gbc.gridy++;
        infoPanel.add(new JLabel("邮箱:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(emailField, gbc);

        // 备注（可编辑）
        gbc.gridx = 0;
        gbc.gridy++;
        infoPanel.add(new JLabel("备注:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH; // 文本域填充
        gbc.weighty = 1.0; // 垂直权重
        infoPanel.add(new JScrollPane(remarkArea), gbc); // 文本域加滚动条

        // 按钮面板
        JPanel btnPanel = new JPanel();
        JButton saveBtn = new JButton("保存修改");
        saveBtn.addActionListener(this::saveChanges);
        btnPanel.add(saveBtn);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        // 添加信息面板到主面板（加滚动条，避免内容溢出）
        mainPanel.add(new JScrollPane(infoPanel), BorderLayout.CENTER);
        this.add(mainPanel); // 最终添加到窗口
    }

    // 修复：数据库查询方法，增加异常捕获和空值处理
    private Employee loadEmployeeInfo() {
        Employee emp = new Employee();
        String sql = "SELECT p.*, d.name as dept_name, j.description as job_name " +  // 移除 m.name 相关
                "FROM person p " +
                "LEFT JOIN department d ON p.department = d.id " +
                "LEFT JOIN job j ON p.job = j.code " +
                "WHERE p.id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // 基础信息
                emp.setId(rs.getInt("id"));
                emp.setName(rs.getString("name"));
                emp.setPassword(rs.getString("password"));
                emp.setSex(rs.getString("sex"));
                emp.setBirthday(rs.getDate("birthday"));
                emp.setAddress(rs.getString("address"));
                emp.setTel(rs.getString("tel"));
                emp.setEmail(rs.getString("email"));
                emp.setRemark(rs.getString("remark"));
                // 关联信息
                emp.setDepartmentName(rs.getString("dept_name"));
                emp.setJobName(rs.getString("job_name"));
//                emp.setSuperiorName(rs.getString("manager_name")); // 上级姓名
                emp.setAuthority(rs.getString("authority"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载个人信息失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
        return emp;
    }

    // 修复：表单赋值，增加空值处理
    private void loadInfoToForm() {
        // 不可编辑字段（赋值+设为不可编辑）
        idField.setText(employee.getId() == 0 ? "" : String.valueOf(employee.getId()));
        idField.setEditable(false);

        nameField.setText(employee.getName() == null ? "" : employee.getName());
        nameField.setEditable(false);

        departmentField.setText(employee.getDepartmentName() == null ? "" : employee.getDepartmentName());
        departmentField.setEditable(false);

        jobField.setText(employee.getJobName() == null ? "" : employee.getJobName());
        jobField.setEditable(false);

        superiorField.setText(employee.getSuperiorName() == null ? "无" : employee.getSuperiorName());
        superiorField.setEditable(false);

        // 可编辑字段（赋值）
        passwordField.setText(employee.getPassword() == null ? "" : employee.getPassword());
        remarkArea.setLineWrap(true); // 自动换行
        remarkArea.setText(employee.getRemark() == null ? "" : employee.getRemark());

        // 性别下拉框（空值处理）
        if (employee.getSex() != null) {
            sexCombo.setSelectedItem(employee.getSex());
        }

        // 出生日期（格式化，空值处理）
        if (employee.getBirthday() != null) {
            birthdayField.setText(new SimpleDateFormat("yyyy-MM-dd").format(employee.getBirthday()));
        }

        addressField.setText(employee.getAddress() == null ? "" : employee.getAddress());
        telField.setText(employee.getTel() == null ? "" : employee.getTel());
        emailField.setText(employee.getEmail() == null ? "" : employee.getEmail());
    }

    // 修复：保存方法，增加参数校验和异常处理
    private void saveChanges(ActionEvent e) {
        // 收集表单数据（空值处理）
        String newPassword = new String(passwordField.getPassword()).trim();
        if (newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "密码不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String newSex = (String) sexCombo.getSelectedItem();
        String newBirthday = birthdayField.getText().trim();
        // 出生日期格式校验
        if (!newBirthday.isEmpty()) {
            try {
                new SimpleDateFormat("yyyy-MM-dd").parse(newBirthday);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "出生日期格式错误（yyyy-MM-dd）！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        String newAddress = addressField.getText().trim();
        String newTel = telField.getText().trim();
        String newEmail = emailField.getText().trim();
        String newRemark = remarkArea.getText().trim();

        // 保存到数据库
        String sql = "UPDATE person SET password=?, sex=?, birthday=?, " +
                "address=?, tel=?, email=?, remark=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, newSex);
            pstmt.setString(3, newBirthday.isEmpty() ? null : newBirthday);
            pstmt.setString(4, newAddress);
            pstmt.setString(5, newTel);
            pstmt.setString(6, newEmail);
            pstmt.setString(7, newRemark);
            pstmt.setInt(8, employeeId);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "信息更新成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                // 更新后重新加载信息
                this.employee = loadEmployeeInfo();
                loadInfoToForm();
            } else {
                JOptionPane.showMessageDialog(this, "信息更新失败，无数据变更！", "提示", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "更新失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 退出登录
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "确定要退出登录吗？", "确认", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new LoginFrame().setVisible(true);
        }
    }

    // 内部：补全Employee类的简易模型（如果你的Employee类没有这些方法，需要同步添加）
    static class Employee {
        private int id;
        private String name;
        private String password;
        private String sex;
        private Date birthday;
        private String address;
        private String tel;
        private String email;
        private String remark;
        private String departmentName;
        private String jobName;
        private String superiorName;
        private String authority;

        // Getter & Setter（必须齐全，否则会报空值）
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getSex() { return sex; }
        public void setSex(String sex) { this.sex = sex; }
        public Date getBirthday() { return birthday; }
        public void setBirthday(Date birthday) { this.birthday = birthday; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getTel() { return tel; }
        public void setTel(String tel) { this.tel = tel; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getRemark() { return remark; }
        public void setRemark(String remark) { this.remark = remark; }
        public String getDepartmentName() { return departmentName; }
        public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
        public String getJobName() { return jobName; }
        public void setJobName(String jobName) { this.jobName = jobName; }
        public String getSuperiorName() { return superiorName; }
        public void setSuperiorName(String superiorName) { this.superiorName = superiorName; }
        public String getAuthority() { return authority; }
        public void setAuthority(String authority) { this.authority = authority; }
    }
}