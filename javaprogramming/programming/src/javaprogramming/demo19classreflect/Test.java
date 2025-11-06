package javaprogramming.demo19classreflect;

import java.io.File;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        //目标：编写程序，使用反射获取任意类的所有方法和属性信息。

        try {
            System.out.println("请输入完整类名（包名.类名/例如：javaprogramming.demo19classreflect.getClassField）：");
            Scanner sc = new Scanner(System.in);
            String className = sc.next().trim();
            System.out.println("类名：" + className);
            Class<?> clazz = Class.forName(className);

            // 获取类名
            getClassName.getName(clazz);
            // 获取所有方法
            getClassMethon.getMethon(clazz);
            // 获取所有属性
            getClassField.getField(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
