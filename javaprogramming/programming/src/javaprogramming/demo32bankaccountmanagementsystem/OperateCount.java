package javaprogramming.demo32bankaccountmanagementsystem;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class OperateCount {
    public static Map<String, Count> counts = new HashMap<>();

    // 静态代码块：加载账户数据
    static {
        try {
            BufferedReader br = new BufferedReader(new FileReader("D:\\Code\\Java\\javaprogramming\\programming\\src\\javaprogramming\\demo32bankaccountmanagementsystem\\account.txt"));
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                String[] split = line.split(",");
                Count count = new Count(split[0], split[1], Double.parseDouble(split[2]));
                counts.put(count.getID(), count);
            }
            br.close();
        } catch (Exception e) {
            System.err.println("加载账户数据失败：" + e.getMessage());
        }
    }

    // 存款（同步代码块）
    public void Deposit(String ID, double amount) {
        synchronized (this) { // 以当前对象为锁
            if (!counts.containsKey(ID)) {
                System.out.println("用户不存在！");
                return;
            }
            Count count = counts.get(ID);
            count.setBalance(count.getBalance() + amount);
            counts.put(ID, count);
            System.out.println("存款成功！余额为：" + count.getBalance());
            Save();
        }
    }

    // 取款（同步代码块）
    public void Withdraw(String ID, double amount) {
        synchronized (this) { // 以当前对象为锁
            Count count = counts.get(ID);
            if (count == null) {
                System.out.println("用户不存在！");
                return;
            }
            if (count.getBalance() >= amount) {
                count.setBalance(count.getBalance() - amount);
                counts.put(ID, count);
                System.out.println("取款成功！余额为：" + count.getBalance());
                Save();
            } else {
                System.out.println("余额不足！取款失败！");
            }
        }
    }

    // 查询余额（同步代码块）
    public void Query(String ID) {
        synchronized (this) { // 以当前对象为锁
            Count count = counts.get(ID);
            if (count == null) {
                System.out.println("用户不存在！");
                return;
            }
            System.out.println("查询成功！余额为：" + count.getBalance());
        }
    }

    // 持久化到文件
    public void Save() {
        try {
            FileWriter fw = new FileWriter("D:\\Code\\Java\\javaprogramming\\programming\\src\\javaprogramming\\demo32bankaccountmanagementsystem\\account.txt");
            for (Count count : counts.values()) {
                fw.write(count.getID() + "," + count.getName() + "," + count.getBalance() + "\n");
            }
            fw.close();
        } catch (Exception e) {
            System.err.println("保存账户数据失败：" + e.getMessage());
        }
    }

    public Map<String, Count> getCounts() {
        return counts;
    }
}