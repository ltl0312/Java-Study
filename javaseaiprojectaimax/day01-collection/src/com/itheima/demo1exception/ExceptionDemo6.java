package com.itheima.demo1exception;

import java.util.Scanner;

public class ExceptionDemo6 {
    public static void main(String[] args) {
        // 目标：掌握异常的处理方案2：捕获异常对象，尝试重新修复。
        // 接收用户的一个定价
        System.out.println("程序开始。。。。");

        while (true) {
            try {
                double price = userInputPrice();
                System.out.println("用户成功设置了商品定价：" + price);
                break;
            } catch (Exception e) {
                System.out.println("您输入的数据是瞎搞的，请不要瞎输入价格！");
            }
        }

        System.out.println("程序结束。。。。");
    }

    public static double userInputPrice(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请您输入商品定价：");
        double price = sc.nextDouble();
        return price;
    }
}
