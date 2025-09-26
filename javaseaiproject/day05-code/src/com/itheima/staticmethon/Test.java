package com.itheima.staticmethon;

public class Test {
    public static void main(String[] args) {
        //认识static修饰和不修饰方法的区别
        //1.类名.静态方法名()
        Student.printHelloWorld();

        //2.对象.静态方法名()(不推荐)
        Student s1 = new Student();
        s1.printHelloWorld();

        //3.对象.实例方法名()
        s1.setScore(59.5);
        s1.printPass();

        //规范:如果这个方法只是为了做一个功能且不需要直接访问对象数据,那么这个方法就可以直接定义为静态方法
        //如果这个方法是对象的行为,需要访问对象的数据,那么这个方法就必须定义为实例方法

    }
}
