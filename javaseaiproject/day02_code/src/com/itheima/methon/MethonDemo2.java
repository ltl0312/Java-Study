package com.itheima.methon;

public class MethonDemo2 {
    public static void main(String[] args) {
        //目标:认识方法重载

    }
    //定义一个方法,打印一个整数
    public static void print(int a){
        System.out.println(a);
    }

    //定义一个重载的方法,打印一个字符串
    public static void print(String a){
        System.out.println(a);
    }

    //定义一个重载的方法
    public static void print(double a){
        System.out.println(a);
    }

    //注意:方法重载只关心方法名称相同,形参列表不同(类型不同,个数不同,顺序不同),其他无所谓
}
