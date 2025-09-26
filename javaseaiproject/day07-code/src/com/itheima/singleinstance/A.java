package com.itheima.singleinstance;

//设计成单例设计模式

public class A {
    //1.私有化构造器
    private A(){}
    //2.定义一个静态变量,用于基本本类的一个唯一对象
    //static final A a = new A();
    private static A a = new A();

    //3.定义一个静态方法,返回唯一对象
    public static A getInstance(){
        return a;
    }
}
