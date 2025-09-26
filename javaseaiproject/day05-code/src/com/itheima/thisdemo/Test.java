package com.itheima.thisdemo;

import com.itheima.thisdemo.Student;

public class Test {
    //认识 this关键字,搞清楚this的应用场景
    public static void main(String[] args) {
        Student s1 = new Student();
        s1.print();
        System.out.println(s1);
    }
}
