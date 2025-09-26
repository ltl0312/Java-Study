package com.itheima.demo;

public class SilverCard extends Card{
    public SilverCard(String carID, String name, String phone, double money) {
    }

    @Override
    public void consume(double money) {
        System.out.println("你当前消费:" + money);
        System.out.println("优惠后的价格:" + money * 0.9);
        if (getMoney() < money * 0.9){
            System.out.println("您当前余额不足,请充值");
        }
        //更新金卡的账户余额
        setMoney(getMoney() - money * 0.9);
    }
}
