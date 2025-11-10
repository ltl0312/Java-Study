package com.itheima.text001;

import java.util.Scanner;

public class CalculateQWithAverage {
    public static void main(String[] args) {
        final double CONSTANT = 1.43e-14; // 公式中的常数1.43×10^-14
        Scanner scanner = new Scanner(System.in);
        double[] qValues = new double[3]; // 存储三组q的值

        // 循环输入三组数据并计算每组的q
        for (int i = 0; i < 3; i++) {
            System.out.println("\n===== 第 " + (i + 1) + " 组数据 =====");
            System.out.print("请输入 t_g 的值：");
            double tg = scanner.nextDouble();
            System.out.print("请输入 U 的值：");
            double u = scanner.nextDouble();

            // 计算公式中间项：[t_g(1 + 0.02√t_g)]^(3/2)
            double insideTerm = tg * (1 + 0.02 * Math.sqrt(tg));
            double powerTerm = Math.pow(insideTerm, 3.0 / 2.0);

            // 计算当前组的q
            double q = CONSTANT * u / powerTerm;
            qValues[i] = q;
            System.out.println("第 " + (i + 1) + " 组的 q 值为：" + q);
        }

        // 计算三组q的平均值
        double sum = 0;
        for (double q : qValues) {
            sum += q;
        }
        double average = sum / 3;
        System.out.println("\n三组q的平均值为：" + average);

        scanner.close();
    }
}