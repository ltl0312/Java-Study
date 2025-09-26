package com.itheima.scanner;

import java.util.Scanner;

public class ScannerDemo1 {
    public static void main(String[] args) {
        main1();
    }
    //需求:我是一个零基础小白,帮我写一个程序,可以让用户键盘输入用户名和年龄,并打印出来
    public static void main1() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名:");
        String name = sc.next();
        System.out.println("请输入年龄:");
        int age = sc.nextInt();
        System.out.println("用户名:"+name);
        System.out.println("年龄:"+age);
        System.out.println("--------");
    }

}
