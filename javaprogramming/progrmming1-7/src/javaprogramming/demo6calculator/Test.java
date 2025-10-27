package javaprogramming.demo6calculator;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        double a, b;
        char op;
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入表达式：");
        System.out.println("请输入第一个数字：");
        a = sc.nextDouble();
        System.out.println("请输入运算符：");
        op = sc.next().charAt(0);
        System.out.println("请输入第二个数字：");
        b = sc.nextDouble();
        double sum = Calculator.calculate(a, op, b);
        System.out.println("结果为：" + sum);
    }
}
