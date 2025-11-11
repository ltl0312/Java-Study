package javaprogramming.demo32bankaccountmanagementsystem;

import java.util.Map;
import java.util.Scanner;

public class User extends Thread{
    private OperateCount operateCount;
    private Scanner scanner;

    // 构造函数，用于接收共享的OperateCount实例和Scanner
    public User(OperateCount operateCount, Scanner scanner) {
        this.operateCount = operateCount;
        this.scanner = scanner;
    }
    public User() {
    }

    @Override
    public void run() {
        OperateCount operateCount = new OperateCount();
        Scanner scanner = new Scanner(System.in);
        while ( true){
            System.out.print("请输入用户ID：");
            String ID = scanner.next();
            int sign = 0;
            Map<String, Count> counts = operateCount.getCounts();
            for (Count count1 : counts.values()){
                if (count1.getID().equals(ID)){
                    sign = 1;
                    break;
                }
            }
            if (sign == 0){
                System.out.println("用户不存在！");
                continue;
            }
            System.out.println("欢迎用户：" + ID);
            System.out.println("1. 存款");
            System.out.println("2. 取款");
            System.out.println("3. 查询余额");
            System.out.println("4. 退出");
            System.out.print("请选择：");
            int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.print("请输入存款金额：");
                        double amount = scanner.nextDouble();
                        operateCount.Deposit(ID, amount);
                        break;
                    case 2:
                        System.out.print("请输入取款金额：");
                        amount = scanner.nextDouble();
                        operateCount.Withdraw(ID, amount);
                        break;
                    case 3:
                        operateCount.Query(ID);
                        break;
                    case 4:
                        System.out.println("正在退出...");
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        operateCount.Save();
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
