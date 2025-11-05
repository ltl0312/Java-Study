package javaprogramming.demo19classreflect;

public class getClassName {
    //获取类名
    public static void getName(Class<?> clazz) {
        // 获取当前类名
        System.out.println("=== 类名 ===");
        String className = getClassName.class.getName();
        System.out.println("当前类名：" + className);
    }
}
