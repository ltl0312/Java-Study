package javaprogramming.demo14findthemaximum;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        //目标：编写一个泛型方法，接受任意类型数组，返回数组中的最大值。
        FindMax fm = new FindMax();
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = sc.nextInt();
        System.out.println("请输入数据：");
        String[] s = new String[length];
        for (int i = 0; i < length; i++) {
            s[i] = sc.next();
        }
        System.out.println(fm.findMax(s));
    }
}
