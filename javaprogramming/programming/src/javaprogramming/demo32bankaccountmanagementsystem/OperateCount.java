package javaprogramming.demo32bankaccountmanagementsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class OperateCount {
    Map<String, Count> counts = new HashMap<>();


    {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("programming\\src\\javaprogramming\\demo32bankaccountmanagementsystem\\counts.txt")));

            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                Count count = new Count(split[0], split[1], Double.parseDouble(split[2]));
                counts.put(count.getID(), count);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //存款
    public void Deposit(String ID, double amount){
        Count count = counts.get(ID);
        count.setBalance(count.getBalance() + amount);
        counts.put(ID, count);
        System.out.println("存款成功！余额为：" + count.getBalance());
    }
    //取款
    public void Withdraw(String ID, double amount){
        Count count = counts.get(ID);
        if (count.getBalance() >= amount) {
            count.setBalance(count.getBalance() - amount);
            counts.put(ID, count);
            System.out.println("取款成功！余额为：" + count.getBalance());
        } else {
            System.out.println("余额不足！取款失败！");
        }
    }
    //查询余额
    public void Query(String ID){
        Count count = counts.get(ID);
        if (count == null) {
            System.out.println("用户不存在！");
            return;
        }
        System.out.println("查询成功！余额为：" + count.getBalance());
    }
}
