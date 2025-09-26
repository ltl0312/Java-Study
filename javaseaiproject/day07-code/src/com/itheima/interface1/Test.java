package com.itheima.interface1;

public class Test {
    public static void main(String[] args) {
        // 目标：认识接口，搞清楚接口的特点，基本使用。
        System.out.println(A.SCHOOL_NAME);
        //接口也不能创建对象
        //接口是用来被类实现的
        A a = new C();
        C c = new C();
        a.run();
        c.run();
        a.go();
        c.go();
        System.out.println(a.go());
        System.out.println(c.go());
    }
}

//C被称为实现类,同时实现了多个接口
//实现类实现多个接口,必须重写完全部接口的全部抽象方法,否则这个类必须定义为抽象类
class C implements A,B{

    @Override
    public void run() {
        System.out.println("C类");
    }

    @Override
    public String go() {
        return "555";
    }

    @Override
    public void play() {
        System.out.println("C类2");
    }
}