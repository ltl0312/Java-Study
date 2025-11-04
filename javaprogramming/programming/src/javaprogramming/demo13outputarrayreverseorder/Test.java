package javaprogramming.demo13outputarrayreverseorder;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        //目标：编写一个程序，逆序输出一个整型数组中的元素。
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入数组大小：");
        int length = sc.nextInt();
        System.out.println("请输入数组：");
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = sc.nextInt();
        }
        System.out.println("逆序：");
        ReverseOrder.ArrayReverseOrder(arr);
    }
}
