package javaprogramming.demo3circular;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        double r;
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入半径：");
        r = sc.nextDouble();
        System.out.println("圆的面积为：" + circulararea.area(r));
    }
}
