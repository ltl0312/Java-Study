package com.itheima.extends3feature;

public class Test {
    public static void main(String[] args) {
        Zi zi = new Zi();
        zi.show();

    }
}


//1.java的类只支持单继承,不支持多继承,但支持多层继承
//2.一个类要么直接继承object类,要么默认继承object类,要么间接继承object类

class Fu{
    String name = "fu的name";
    public void run(){
        System.out.println("fu的run");
    }
}

class Zi extends Fu{
    String name = "zi的name";
    public void show(){
        String name = "show的name";
        System.out.println(name);//show的name
        System.out.println(this.name);//zi的name
        System.out.println(super.name);//fu的name

        run();//就近,会访问zi类的run方法
        super.run();//指定访问fu类的run方法
    }

    public void run(){
        System.out.println("zi的run");
    }
}