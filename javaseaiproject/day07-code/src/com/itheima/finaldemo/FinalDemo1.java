package com.itheima.finaldemo;

public class FinalDemo1 {

    //final修饰静态成员变量,这个变量被称为常量,可以记住一个固定值,并且程序中不能修改了,通常这个值作为系统的配置信息
    public static final String schoolName = "传智播客";
    public static void main(String[] args) {
        //认识final关键字的作用
        //final修饰变量,变量只能被赋值一次,变量值不能被改变
        final double rate = 3.14;
        //rate = 3.15;//第二次赋值,报错
    }
}

//final修饰类,类不能被继承
final class A{}
//class B extends A{}

//final修饰方法,方法不能被重写
class C{
    public final void show(){
        System.out.println("C.show()");
    }
}

class D extends C{
//    @Override
//    public void show() {
//        System.out.println("D.show()");
//    }
}
