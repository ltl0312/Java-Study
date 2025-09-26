package com.itheima.staticmethon;

public class Student {
    private double score;
    //静态方法,有static修饰,属于类持有
    public static void printHelloWorld(){
        System.out.println("HelloWorld");
        System.out.println("HelloWorld");
        System.out.println("HelloWorld");
    }

    //实例方法,没有static修饰,属于对象持有
    public void printPass(){
        System.out.println(score >= 60 ? "通过" : "未通过");
    }

    public void setScore(double score) {
        this.score = score;
    }
}
