package javaprogramming.demo9Implementcustomexceptions;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        //目标：创建一个自定义异常类 NegativeNumberException，当用户输入负数时抛出该异常。
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入一个正数：");
        int a = sc.nextInt();
        try {
            if (a < 0) {
                throw new NegativeNumberException();
            }
            System.out.println("输入的数字是：" + a);
        } catch (NegativeNumberException e) {
            System.out.println("输入的数字不能为负数");
        }
    }
}
