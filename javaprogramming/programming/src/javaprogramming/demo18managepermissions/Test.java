package javaprogramming.demo18managepermissions;

import java.util.Scanner;

import static javaprogramming.demo18managepermissions.UserRole.*;

public class Test {
    public static void main(String[] args) {
        //目标：定义一个枚举类型UserRole，包含ADMIN、USER 和GUEST三个角色，并根据角色输出相应的权限信息。
        UserRole role;
        System.out.println("1.ADMIN");
        System.out.println("2.USER");
        System.out.println("3.GUEST");
        System.out.println("请输入要查看的权限（填标号）：");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                role = ADMIN;
                break;
            case 2:
                role = USER;
                break;
            case 3:
                role = GUEST;
                break;
            default:
                System.out.println("输入错误！");
                return;
        }
        switch (role) {
            case ADMIN:
                System.out.println("管理员权限：拥有所有权限");
                break;
            case USER:
                System.out.println("用户权限：拥有访问和修改权限");
                break;
            case GUEST:
                System.out.println("访客权限：仅有访问权限");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + role);
        }
    }
}
