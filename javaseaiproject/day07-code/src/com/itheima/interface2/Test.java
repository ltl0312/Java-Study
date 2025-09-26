package com.itheima.interface2;

public class Test {
    public static void main(String[] args) {
        //目标:理解Java设计接口的好处,用处
        //1.接口可以实现多继承
        People p = new Student();
        Driver d = new Student();

        //接口可以实现面向接口编程,更利于解耦合
    }
}

interface Driver{}








class People{}

class Student extends People implements Driver{}


