package javaprogramming.demo19classreflect;

import java.lang.reflect.Method;

public class getClassMethon {
    //获取类的方法
    public static void getMethon(Class<?> clazz) {
        System.out.println("\n=== 所有方法 ===");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("方法: " + method.getName() +
                    ", 返回类型: " + method.getReturnType().getSimpleName() +
                    ", 参数个数: " + method.getParameterCount());
        }
    }
}
