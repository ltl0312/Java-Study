package javaprogramming.demo10reflectionInvokemethod;

import java.lang.reflect.Method;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        //目标：使用反射机制，动态调用BankAccount类中的deposit方法。
        try {
            Class account = BankAccount.class;
            Method method = account.getMethod("deposit", Account.class, double.class);
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入账户名称：");
            String name = sc.next();
            System.out.println("请输入账户余额：");
            double balance = sc.nextDouble();
            System.out.println("请输入存款金额：");
            double amount = sc.nextDouble();
            method.invoke(new BankAccount(),new Account(name, balance), amount);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
