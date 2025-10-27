package javaprogramming.demo5judgeleapyear;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        int year;
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入一个年份：");
        year = sc.nextInt();
        if (Judeg.isLeapYear(year)) {
            System.out.println(year + "年是闰年");
        } else {
            System.out.println(year + "年不是闰年");
        }
    }
}
