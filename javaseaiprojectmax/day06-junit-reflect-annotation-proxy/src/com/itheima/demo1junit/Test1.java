package com.itheima.demo1junit;

public class Test1 {
    private String name;
    private int age;
    private String hobby;

    //构造器
    public Test1() {

    }
    public Test1(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    private Test1(String name, int age) {
        this.name = name;
        this.age = age;
    }

}
