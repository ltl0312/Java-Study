package com.itheima.interface5;

public class Test {
    public static void main(String[] args) {
        //目标:理解接口的几点注意事项
        //4、一个类实现了多个接口，如果多个接口中存在同名的默认方法，可以不冲突，这个类重写该方法即可。
        zi z = new zi();
        z.show1();
    }
}

//1、接口与接口可以多继承：一个接口可以同时继承多个接口[重点]。
//类与类:单继承
//类与接口:多实现
//接口与接口:多继承,一个接口可以继承多个接口
interface A {
    public abstract void show1();//public abstract可省略
}
interface B {
    void show2();
}
interface C extends A, B{
    void show3();
}
class D implements C {
    //重写方法
    @Override
    public void show1() {
    }
    @Override
    public void show2() {
    }
    @Override
    public void show3() {
    }
}

//2.一个接口继承多个接口,如果多个接口存在方法签名冲突,则此时不支持多继承,也不支持多实现
interface A1 {
    void show1();
}
interface B1 {
    void show2();
}
interface C1 extends A1,B1 {
    void show3();
}
class D1 implements C1 {
    //重写方法
    @Override
    public void show1() {
    }
    @Override
    public void show2() {
    }
    @Override
    public void show3() {
    }
}

//3、一个类继承了父类，又同时实现了接口，如果父类中和接口中有同名的默认方法，实现类会优先用父类的。
interface A2 {
    default void show1(){
        System.out.println("接口中的A2show方法");
    }
}

class fu{
    public void show1(){
        System.out.println("父类中的show方法");
    }
}

class zi extends fu implements A2{
}



