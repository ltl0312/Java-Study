package javaprogramming.demo2add;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        int a, b;
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入两个数字：");
        a = sc.nextInt();
        b = sc.nextInt();
        int sum = Add.add(a, b);
        System.out.println("两个数字的和为：" + sum);
    }
}
