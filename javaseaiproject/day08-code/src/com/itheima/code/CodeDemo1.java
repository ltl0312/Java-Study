package com.itheima.code;

import java.lang.reflect.Array;
import java.util.Arrays;

public class CodeDemo1 {
    public static String schoolName;
    public static String [] cards = new String[54];
    //静态代码块:与类一起加载,自动执行一次
    //基本作用:完成对类的静态资源的初始化
    static {
        System.out.println("===静态代码块执行了===");
        schoolName = "黑马程序员";
        cards[0] = "大王";
        cards[1] = "小王";
        cards[2] = "A";
        cards[3] = "2";
        cards[4] = "3";
        cards[5] = "4";

    }
    public static void main(String[] args) {
        //目标:认识代码块,搞清楚代码块的基本作用
        System.out.println("===main方法执行了===");
        System.out.println(schoolName);
        System.out.println(Arrays.toString(cards));
    }
}
