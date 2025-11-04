package javaprogramming.demo15listdeduplication;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Test {
    public static void main(String[] args) {
        //目标：输入一组数字，将其存入ArrayList并移除重复元素。
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请输入数字（输入-1时退出）：");
            int num = sc.nextInt();
            if (num == -1) {
                break;
            }
            list.add(num);
            list = (ArrayList<Integer>) list.stream()
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
            System.out.println("去重后为：");
            System.out.println(list);
        }

    }
}
