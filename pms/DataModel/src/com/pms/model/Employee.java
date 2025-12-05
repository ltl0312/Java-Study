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

    public String getAuthority() {
        return authority;
    }
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

    public String getJobName() {
        switch (jobCode){
            case 1:
                jobName = "部门经理";
                break;
            case 2:
                jobName = "高级工程师";
                break;
            case 3:
                jobName = "人事专员";
                break;
            case 4:
                jobName = "会计";
                break;
            case 5:
                jobName = "销售代表";
                break;
                case 6:
                    jobName = "行政助理";
                    break;
                case 7:
                    jobName = "初级工程师";
                    break;
                case 8:
                    jobName = "财务主管";
                    break;
                case 9:
                    jobName = "测试工程师";
                    break;
                case 10:
                    jobName = "算法工程师";
                    break;
                case 11:
                    jobName = "市场专员";
                    break;
                case 12:
                    jobName = "销售经理";
                    break;
                case 13:
                    jobName = "后勤主管";
                    break;
                case 14:
                    jobName = "AI 训练师";
                    break;
                case 15:
                    jobName = "数据架构师";
                    break;
                default:
                    jobName = "未知";
                    break;
        }
        return jobName;
    }
    public void setJobName(String jobName) { this.jobName = jobName; }

    public String getEduLevelName() {
        switch (eduLevelCode){
            case 0:
                eduLevelName = "小学";
                break;
            case 1:
                eduLevelName = "初中";
                break;
            case 2:
                eduLevelName = "高中";
                break;
            case 3:
                eduLevelName = "职高";
                break;
            case 4:
                eduLevelName = "大专";
                break;
            case 5:
                eduLevelName = "大本";
                break;
            case 6:
                eduLevelName = "硕士";
                break;
            case 7:
                eduLevelName = "博士";
                break;
            case 8:
                eduLevelName = "博士后";
                break;
            default:
                eduLevelName = "未知";
        }
        return eduLevelName;
    }
    public void setEduLevelName(String eduLevelName) { this.eduLevelName = eduLevelName; }
    public boolean isAdmin() {
        return "manager".equals(authority) || "admin".equals(authority);
    }

    public boolean isStaff() {
        return "staff".equals(authority);
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



