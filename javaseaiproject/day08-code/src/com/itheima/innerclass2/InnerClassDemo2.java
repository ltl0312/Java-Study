package com.itheima.innerclass2;

public class InnerClassDemo2 {
    public static void main(String[] args) {
        //目标:搞清楚静态内部类的语法
        //创建对象:静态内部类创建对象的格式
        //外部类名.静态内部类名 静态对象名 = new 外部类名.静态内部类名();
        Outer.Inner oi = new Outer.Inner();
        oi.show();
        //1.静态内部类中可以直接访问外部类中静态成员
        //2.静态内部类中不可以直接访问外部类中实例成员
    }
}
