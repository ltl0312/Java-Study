
package javaprogramming.demo32bankaccountmanagementsystem;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {

        //目标：实现一个简单的银行账户管理系统，支持以下功能
        // 创建并启动多个线程
        OperateCount operateCount = new OperateCount();
        Scanner scanner = new Scanner(System.in);

        User user1 = new User(operateCount, scanner);
        User user2 = new User(operateCount, scanner);

        user1.start();
        user2.start();
    }
}