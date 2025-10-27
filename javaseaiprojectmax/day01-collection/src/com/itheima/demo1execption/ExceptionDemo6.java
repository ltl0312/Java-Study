package com.itheima.demo1execption;

import java.util.Scanner;

public class ExceptionDemo6 {
    public static void main(String[] args) {
        //目标:掌握异常处理方案二:捕获异常对象,尝试重新修复
        //接受用户的一个定价
        System.out.println("程序开始...");
        while (true) {
            try {
                double price = uerInputPrice();
                System.out.println("用户输入的价格是:" + price);
                break;
            } catch (Exception e) {
                System.out.println("用户输入价格有误,请重新输入");
                //e.printStackTrace();
            }
        }
        System.out.println("程序结束...");
    }

    public static double uerInputPrice(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入商品价格:");
        double price = sc.nextDouble();
        return price;


    }
}
