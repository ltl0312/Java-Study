package com.itheima.demo6lock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Account {
    private String CardID;
    private double money;
    private final Lock lk = new ReentrantLock();//final保护锁对象,防止锁对象被修改

    public void drawMoney(double money) {
        //拿到当前是谁来取钱
        String name = Thread.currentThread().getName();
        //获取锁(上锁)
        lk.lock();
        try {
            //判断余额是否足够
            if (this.money >= money) {
                System.out.println(name + "取钱成功，取钱金额：" + money);
                this.money -= money;
                System.out.println(name + "取钱成功，余额为：" + this.money);
            }
            else {
                System.out.println(name + "取钱失败，余额不足！");
            }
        } finally {
            //不管try中代码是否异常,finally中代码都会执行
            //释放锁(解锁)
            lk.unlock();
        }
    }
}
