package com.itheima.itcase;

import java.util.Scanner;

public class SimpleCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入第一个数字:");
        double a = sc.nextDouble();
        System.out.println("请输入第二个数字:");
        double b = sc.nextDouble();
        System.out.println("请输入运算符:");
        char op = sc.next().charAt(0);
        System.out.println(calculate(a,b,op));

    }
    public static double calculate(double a,double b,char op){
        double result = 0;
        switch (op){
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                result = a / b;
                break;
            default:
                System.out.println("输入的运算符有误");
        }
        return result;
    }
}
