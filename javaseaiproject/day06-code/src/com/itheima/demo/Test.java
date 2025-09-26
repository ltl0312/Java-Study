package com.itheima.demo;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        //目标:加油站支付小程序
        //1.创建卡片类
        //2.定义一个卡片父类
        //3.定义一个金卡类
        GoldCard goldCard = new GoldCard("G001","小王","13800000001",1000);
        //4.定义一个银卡类
        SilverCard silverCard = new SilverCard("S001","小张","13800000002",1000);
        //5.定义一个支付机
        Pay(goldCard);
        Pay(silverCard);
    }

    public static void Pay(Card c){
        System.out.println("请刷卡,请您输入当前消费的金额:");
        Scanner sc = new Scanner(System.in);
        double money = sc.nextDouble();
        c.consume(money);
    }
}
