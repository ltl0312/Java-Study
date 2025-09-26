package com.itheima.demo;

import java.util.Scanner;

public class AllTest {
    public static void main(String[] args) {
        //目标:完成健康计算器
        //1.提示用户输入数据:身高,体重,年龄,性别
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入身高:");
        double height = sc.nextDouble();
        System.out.println("请输入体重:");
        double weight = sc.nextDouble();
        System.out.println("请输入年龄:");
        int age = sc.nextInt();
        System.out.println("请输入性别:");
        String sex = sc.next();

        //根据用户输入的数据,计算BMI值
        double bmi = getBMI(height,weight);
        System.out.println("BMI:"+bmi);
        //根据用户输入的数据,计算BMR值
        double bmr = getBMR(height,weight,age,sex);
        System.out.println("BMR:"+bmr);
    }

    //2.根据个人信息计算BMI值(把数据交给一个独立的方法计算并返回结果)
    public static double getBMI(double height,double weight){
        return weight / (height * height);
    }

    //3.根据个人信息计算BMR值(把数据交给一个独立的方法计算并返回结果)
    public static double getBMR(double height,double weight,int age,String sex){
        if ("男".equals(sex)){
            return 66 + (13.7 * weight) + (5 * height) - (6.8 * age);
        }else{
            return 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age);
        }
    }

}
