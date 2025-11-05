package javaprogramming.demo19classreflect;

import java.io.File;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        //目标：编写程序，使用反射获取任意类的所有方法和属性信息。

        try {
            System.out.println("请输入完整类名（绝对路径）：");
            Scanner sc = new Scanner(System.in);
            String input = sc.next().trim();
            String className = convertToClassName(input);
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

    private static String convertToClassName(String input) {
        String className = input;

        // 如果是文件路径，转换为类名
        if (className.contains(".") &&
                (className.endsWith(".java") || className.contains(File.separator) || className.contains("/"))) {

            // 移除 .java 扩展名
            if (className.endsWith(".java")) {
                className = className.substring(0, className.length() - 5);
            }

            // 替换文件分隔符为点号
            className = className.replace('/', '.').replace('\\', '.');

            // 移除常见的源码路径前缀
            if (className.contains("src.")) {
                className = className.substring(className.indexOf("src.") + 4);
            }
        }

        return className;
    }

}
