package com.itheima.javabean;

public class Student {
    //1.私有化成员变量
    private String name;
    private double Chinese;
    private double Math;

    //必须提供无参构造器

    public Student() {
    }


    //可提供有参构造器

    public Student(String name, double chinese, double math) {
        this.name = name;
        Chinese = chinese;
        Math = math;
    }


    //2.提供对应的getter和setter方法

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getChinese() {
        return Chinese;
    }

    public void setChinese(double chinese) {
        Chinese = chinese;
    }

    public double getMath() {
        return Math;
    }

    public void setMath(double math) {
        Math = math;
    }
}
