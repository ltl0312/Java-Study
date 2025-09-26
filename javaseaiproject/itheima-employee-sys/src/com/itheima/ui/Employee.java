package com.itheima.ui;

import java.time.LocalDate;

// 员工实体类
public class Employee {
    private String id;
    private String name;
    private String gender;
    private int age;
    private String phone;
    private String position;
    private LocalDate hireDate;
    private double salary;
    private String department;

    public Employee(String id, String name, String gender, int age, String phone, String position, LocalDate hireDate, double salary, String department) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.position = position;
        this.hireDate = hireDate;
        this.salary = salary;
        this.department = department;
    }

    // Getter 和 Setter 方法
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return String.format("ID: %s | 姓名: %s | 性别: %s | 年龄: %d | 电话: %s | 职位: %s | 入职日期: %s | 薪水: %.2f | 部门: %s",
                id, name, gender, age, phone, position, hireDate, salary, department);
    }
}



