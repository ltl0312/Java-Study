package javaprogramming.demo23bubblesort;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        //目标：编写程序，使用冒泡排序对整数数组进行升序排列。
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int n = sc.nextInt();
        int[] arr = new int[n];
        System.out.println("请输入数组元素：");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        BubbleSort.bubbleSort(arr);
        System.out.println("排序后的数组为：");
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
