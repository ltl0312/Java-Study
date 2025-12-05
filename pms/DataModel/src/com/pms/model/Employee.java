package com.pms.model;

import java.util.Date;

public class Employee {
    private int id;
    private String password = "123456";
    private String authority = "staff";
    private String name;
    private String sex;
    private Date birthday = new Date(0);
    private int departmentId;
    private int jobCode;
    private int eduLevelCode;
    private String specialty;
    private String address;
    private String tel;
    private String email;
    private char state = 't';
    private String remark;

    // 部门、职务、教育程度的名称（用于显示）
    private String departmentName;
    private String jobName;
    private String eduLevelName;

    // 构造方法
    public Employee() {}

    public Employee(int id, String name, String sex, String departmentName, String jobName) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.departmentName = departmentName;
        this.jobName = jobName;
    }

    public Employee(int id, String name, String sex, int departmentId, int jobCode, String tel) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.departmentId = departmentId;
        this.jobCode = jobCode;
        this.tel = tel;
    }
    public Employee(int id, String name, String sex, int departmentId, int jobCode) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.departmentId = departmentId;
        this.jobCode = jobCode;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAuthority() { return authority; }
    public void setAuthority(String authority) { this.authority = authority; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public Date getBirthday() { return birthday; }
    public void setBirthday(Date birthday) { this.birthday = birthday; }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    public int getJobCode() { return jobCode; }
    public void setJobCode(int jobCode) { this.jobCode = jobCode; }

    public int getEduLevelCode() { return eduLevelCode; }
    public void setEduLevelCode(int eduLevelCode) { this.eduLevelCode = eduLevelCode; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public char getState() { return state; }
    public void setState(char state) { this.state = state; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getJobName() { return jobName; }
    public void setJobName(String jobName) { this.jobName = jobName; }

    public String getEduLevelName() { return eduLevelName; }
    public void setEduLevelName(String eduLevelName) { this.eduLevelName = eduLevelName; }
    public boolean isAdmin() {
        return "manager".equals(authority) || "admin".equals(authority);
    }

    public boolean isStaff() {
        return "staff".equals(authority);
    }

    // 根据数据库关联关系建立映射的方法
    public void setDepartmentNameById(int deptId, java.util.Map<Integer, String> departmentMap) {
        this.departmentId = deptId;
        this.departmentName = departmentMap != null ? departmentMap.get(deptId) : null;
    }

    public void setJobNameByCode(int jobCode, java.util.Map<Integer, String> jobMap) {
        this.jobCode = jobCode;
        this.jobName = jobMap != null ? jobMap.get(jobCode) : null;
    }

    public void setEduLevelNameByCode(int eduLevelCode, java.util.Map<Integer, String> eduLevelMap) {
        this.eduLevelCode = eduLevelCode;
        this.eduLevelName = eduLevelMap != null ? eduLevelMap.get(eduLevelCode) : null;
    }

    // 从ID映射更新显示名称
    public void updateDisplayNames(java.util.Map<Integer, String> departmentMap,
                                   java.util.Map<Integer, String> jobMap,
                                   java.util.Map<Integer, String> eduLevelMap) {
        this.departmentName = departmentMap != null ? departmentMap.get(this.departmentId) : null;
        this.jobName = jobMap != null ? jobMap.get(this.jobCode) : null;
        this.eduLevelName = eduLevelMap != null ? eduLevelMap.get(this.eduLevelCode) : null;
    }

    // 重写toString方法便于调试
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", jobCode=" + jobCode +
                ", jobName='" + jobName + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}



