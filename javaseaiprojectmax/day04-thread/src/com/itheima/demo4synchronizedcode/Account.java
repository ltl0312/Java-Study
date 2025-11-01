package com.itheima.demo4synchronizedcode;

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
        //对于实例方法建议使用 this作为所=锁的对象
        //对于静态方法建议使用 类名.class 作为所=锁的对象
        synchronized (this) {
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
}
