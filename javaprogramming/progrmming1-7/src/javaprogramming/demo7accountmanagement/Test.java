package javaprogramming.demo7accountmanagement;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入账户名称：");
        account.setName(scanner.next());
        System.out.println("请输入余额：");
        account.setBalance(scanner.nextDouble());
        OperateAccount operateAccount = new OperateAccount();
        while (true) {
            System.out.println("1.存款");
            System.out.println("2.取款");
            System.out.println("3.查询余额");
            System.out.println("4.退出");
            System.out.println("请选择：");
            switch (scanner.nextInt()){
                case 1:
                    System.out.println("请输入存款金额：");
                    operateAccount.deposit(account, scanner.nextDouble());
                    System.out.println("存款成功,请继续操作：");
                    break;
                case 2:
                    System.out.println("请输入取款金额：");
                    operateAccount.withdraw(account, scanner.nextDouble());
                    break;
                case 3:
                    operateAccount.checkBalance(account);
                    System.out.println("请继续操作：");
                    break;
                case 4:
                    System.out.println("退出");
                    return;
                default:
                    System.out.println("无效选择");
                    break;
            }
        }
    }
}

