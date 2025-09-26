package com.itheima.array;

import java.util.Scanner;

public class ArrayDemo1 {
    public static void main(String[] args) {
        InputScore();
    }
    public static void InputScore() {
        double[] scores = new double[8];
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < scores.length; i++) {
            System.out.println("请输入第" + (i + 1) + "个同学的分数:");
            scores[i] = sc.nextDouble();
        }

        double SumScore = 0.0;
        for (int i = 0; i < scores.length; i++) {
            SumScore += scores[i];
        }
        System.out.println("总分是:" + SumScore);
        System.out.println("平均分是:" + SumScore / scores.length);
        MaxArray(scores);
        MinArray(scores);

    }

    public static void MaxArray(double [] arr){
        double max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            //先将数组元素赋给data,再用data与max比较,这样性能更好
            double data = arr[i];
            if (max < data) {
                max = data;
            }
        }
        System.out.println("最大值是:" + max);
    }

    public static void MinArray(double [] arr){
        double min = arr[0];
        for (int i = 0; i < arr.length; i++) {
            double data = arr[i];
            if (min > data) {
                min = data;
            }
        }
        System.out.println("最小值是:" + min);
    }

}
