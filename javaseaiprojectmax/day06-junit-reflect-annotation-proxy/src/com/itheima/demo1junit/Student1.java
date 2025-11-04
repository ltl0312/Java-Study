package com.itheima.demo1junit;

public class Student1 {
    private String name;
    private int age;
    private String hobby;
    public Student1() {
    }
    public Student1(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
    private Student1(String name) {
        this.name = name;
    }
}
