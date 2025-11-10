package javaprogramming.demo32bankaccountmanagementsystem;

import java.util.Scanner;

public class User extends Thread{
    public static void main(String[] args) {
        OperateCount operateCount = new OperateCount();
        Scanner scanner = new Scanner(System.in);
        while ( true){
            System.out.println("1. 存款");
            System.out.println("2. 取款");
            System.out.println("3. 查询余额");
            System.out.println("4. 退出");
            System.out.print("请选择：");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("请输入用户ID：");
                    String ID = scanner.next();
                    System.out.print("请输入存款金额：");
                    double amount = scanner.nextDouble();
                    operateCount.Deposit(ID, amount);
                    break;
                case 2:
                    System.out.print("请输入用户ID：");
                    ID = scanner.next();
                    System.out.print("请输入取款金额：");
                    amount = scanner.nextDouble();
                    operateCount.Withdraw(ID, amount);
                    break;
                case 3:
                    System.out.print("请输入用户ID：");
                    ID = scanner.next();
                    operateCount.Query(ID);
                    break;
                case 4:
                    System.out.println("感谢使用银行管理系统！");
                    System.exit(0);
                    return;
                default:
                    System.out.println("无效的选择，请重新输入！");
                    break;
            }
        }
    }
}
