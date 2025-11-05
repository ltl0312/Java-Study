package javaprogramming.demo19classreflect;

import java.lang.reflect.Field;

public class getClassField {
    //获取类的所有属性信息
    public static void getField(Class<?> clazz) {
        System.out.println("\n=== 所有属性 ===");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("属性: " + field.getName() + ", 类型: " + field.getType().getSimpleName());

        }
    }
}
