package com.itheima.demo;

public class GoldCard extends Card{
    public GoldCard(String carID, String name, String phone, double money) {
        super(carID, name, phone, money);

    }

    @Override
    public void consume(double money) {
        System.out.println("你当前消费:" + money);
        System.out.println("优惠后的价格:" + money * 0.8);
        if (getMoney() < money * 0.8){
            System.out.println("您当前余额不足,请充值");
            return;
        }
        //更新金卡的账户余额
        setMoney(getMoney() - money * 0.8);
        //判断消费金额是否大于200,大于200送洗车券
        if (money * 0.8 > 200) {
            printTicket();
        }
        else{
            System.out.println("您当前消费未满200,不能免费洗车");
        }

    }

    //打印洗车票
    public void printTicket() {

        System.out.println("恭喜你，获得一张洗车券");
    }
}
