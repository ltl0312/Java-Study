package com.itheima.stringdemo;

import java.util.Scanner;

public class StringDemo1 {
    public static void main(String[] args) {
        //目标:掌握创建字符串对象,封装要处理的字符串数据,调用String提供的方法处理字符串
        //1.推荐方式一:直接用""就可以创建字符串对象,封装字符串数据
        String s1 = "hello world";
        System.out.println(s1);
        System.out.println(s1.length());

        //2.方式二:通过构造器初始化对象
        String s2 = new String("hello world");//不推荐
        System.out.println(s2);

        char [] chs = {'h', 'e', 'l', 'l', 'o', ' ', 'w', 'o', 'r', 'l', 'd'};
        String s3 = new String(chs);
        System.out.println(s3);

        byte [] bytes = {104, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100};
        String s4 = new String(bytes);
        System.out.println(s4);

        System.out.println("===============================");
        //只有""给出的字符串对象放在运行时常量池中,而且相同内容只放一份,其他方式创建的字符串对象,都在堆中
        String s5 = "hello world";
        System.out.println(s5 == s1);
        String s6 = new String("hello world");
        System.out.println(s5 == s6);
        String s7 = new String("hello world");
        System.out.println(s6 == s7);



        //字符串内容的比较不能用"==",因为"=="比较的是地址,要用equals方法
        //比较字符串内容是否相同应该使用String提供的equals方法,只关心内容是否一样,不关心地址
        System.out.println("========================");
        System.out.println(s5.equals(s6));
        System.out.println(s3.equals(s7));
        System.out.println(s3.equals(s4));

        System.out.println("========================");
        //18663656520 -> 186****6520
        System.out.println("请您用手机号码登录:");
        Scanner sc = new Scanner(System.in);
        String phone = sc.next();
        System.out.println("系统显示以下手机号码进入:");
        String newPhone = phone.substring(0,3) + "****" + phone.substring(7);
        System.out.println(newPhone);

    }
}
