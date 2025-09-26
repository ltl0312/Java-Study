package com.itheima.object;

public class Test {
    public static void main(String[] args) {
        Star s1 = new Star();
        s1.name = "张三丰";
        s1.sex = "男";
        s1.age = 1000;
        s1.height = 1.8;
        s1.weight = 190.0;
        System.out.println(s1.name);
        System.out.println(s1.sex);
        System.out.println(s1.age);
        System.out.println(s1.height);
        System.out.println(s1.weight);
        System.out.println("--------");
        Star s2 = new Star();
        s2.name = "杨幂";
        s2.sex = "女";
        s2.age = 28;
        s2.height = 1.6;
        s2.weight = 50.0;
        System.out.println(s2.name);
        System.out.println(s2.sex);
        System.out.println(s2.age);
        System.out.println(s2.height);
        System.out.println(s2.weight);
        System.out.println("--------");
    }
}
