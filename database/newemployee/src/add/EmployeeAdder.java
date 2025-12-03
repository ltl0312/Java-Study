package add;

import java.sql.*;

public class EmployeeAdder {
    private int id; // 员工号
    private String password = "123456"; // 必填项
    private String authority = "staff"; // 用户权限,必填项
    private String name; // 必填项
    private String sex = null;
    private String birth = null;
    private String department = null;
    private String job = null; // 职务
    private int edu_level = 1;
    private String specialty = null; // 专业
    private String address = null;
    private String tel = null;
    private String email = null;
    private char state = 't'; // 在职状态,必填项
    private String text = null; // 备注

    public EmployeeAdder() {
    }

    public EmployeeAdder(int id, String name, char state, String password, String authority) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.password = password;
        this.authority = authority;
    }

    public EmployeeAdder(int empId, String pass, String auth, String empName, String empSex, String empBirth,
                         String empDepartment, String empJob, int empEduLevel, String empSpecialty,
                         String empAddress, String empTel, String empEmail, char empState, String empText) {
        this.id = empId;
        this.password = pass;
        this.authority = auth;
        this.name = empName;
        this.sex = empSex;
        this.birth = empBirth;
        this.department = empDepartment;
        this.job = empJob;
        this.edu_level = empEduLevel;
        this.specialty = empSpecialty;
        this.address = empAddress;
        this.tel = empTel;
        this.email = empEmail;
        this.state = empState;
        this.text = empText;
    }

    public void addEmployee() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://localhost:3306/test";
            String user = "root";
            String pass = "a82711045";
            conn = DriverManager.getConnection(url, user, pass);
            String sql = "insert into employee(id, password, authority, name, sex, birthday, department, job, edu_level, specialty, address, tel, email, state, text) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, password);
            pstmt.setString(3, authority);
            pstmt.setString(4, name);
            pstmt.setString(5, sex);
            pstmt.setString(6, birth);
            pstmt.setString(7, department);
            pstmt.setString(8, job);
            pstmt.setInt(9, edu_level);
            pstmt.setString(10, specialty);
            pstmt.setString(11, address);
            pstmt.setString(12, tel);
            pstmt.setString(13, email);
            pstmt.setString(14, String.valueOf(state));
            pstmt.setString(15, text);

            int count = pstmt.executeUpdate();
            if (count > 0) {
                System.out.println("添加成功");
            } else {
                System.out.println("添加失败");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getEdu_level() {
        return edu_level;
    }

    public void setEdu_level(int edu_level) {
        this.edu_level = edu_level;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}