package javaprogramming.demo27treesetsort;

import java.util.Scanner;
import java.util.TreeSet;

public class Test {
    public static void main(String[] args) {
        //目标：输入一组整数，将其存入TreeSet并自动按升序排列输出。
        TreeSet<Integer> set = new TreeSet<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入整数（输入-1结束）：");
        while (true) {
            int n = sc.nextInt();
            if (n == -1) {
                break;
            }
            set.add(n);
        }
        AutoSort.sort(set);
        System.out.println("排序后的结果为：");
        for (Integer i : set) {
            System.out.print(i + " ");
        }
    }
}
