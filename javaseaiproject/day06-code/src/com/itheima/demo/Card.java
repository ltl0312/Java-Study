package com.itheima.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// lombok技术可以实现自动添加getter和setter方法, toString方法, 有参构造方法, 无参构造方法
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Card {
    private String carID;//车牌号
    private String name;//姓名
    private String phone;//电话号码
    private double money;//余额


    //预存金额
    public void deposit(double money){
        this.money += money;
    }

    //消费金额
    public void consume(double money){
        this.money -= money;
    }



}
