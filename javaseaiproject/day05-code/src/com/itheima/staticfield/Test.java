package com.itheima.staticfield;

public class Test {
    public static void main(String[] args) {
        //认识static修饰成员变量,特点,访问机制,搞清楚作用

        Student.name = "袁华";
        System.out.println(Student.name);

        Student s1 = new Student();
        s1.name = "小王";
        System.out.println(s1.name);
        System.out.println(Student.name);

    }
}
