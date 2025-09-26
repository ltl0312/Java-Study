package com.itheima.polymorphsm1;

public class Test {
    public static void main(String[] args) {
        //目标:认识多态的代码
        //1.对象多态,行为多态
        //前提:有继承关系,存在方法重写,存在父类应用子类对象
        Animal a1 = new Wolf();
        a1.run();//方法:编译时看左边,运行时看右边
        System.out.println(a1.name);//成员变量:编译时看左边,运行时也看左边
        Animal a2 = new Tortoise();
        a2.run();//方法:编译时看左边,运行时看右边
        System.out.println(a2.name);//成员变量:编译时看左边,运行时也看左边
    }
}
