package com.itheima.demo3threadsafe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Account {
    private String CardID;
    private double money;

    public void drawMoney(double money) {
        //拿到当前是谁来取钱
        String name = Thread.currentThread().getName();
        //判断余额是否足够
        if (this.money >= money) {
            System.out.println(name + "取钱成功，取钱金额：" + money);
            this.money -= money;
            System.out.println(name + "取钱成功，余额为：" + this.money);
        }
        else {
            System.out.println(name + "取钱失败，余额不足！");
        }
    }
}
