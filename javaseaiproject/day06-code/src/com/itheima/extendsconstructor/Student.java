package com.itheima.extendsconstructor;

public class Student {
    private String name;
    private char sex;
    private int age;
    private String schoolName;

    public Student() {
    }

    public Student(String name, char sex, int age) {
//        this.name = name;
//        this.sex = sex;
//        this.age = age;
//        this.schoolName = "传智";
        this(name,sex,age,"传智");//调用兄弟构造器
    }

    public Student(String name, char sex, int age, String schoolName) {
        super();
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.schoolName = schoolName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", schoolName='" + schoolName + '\'' +
                '}';
    }
}
