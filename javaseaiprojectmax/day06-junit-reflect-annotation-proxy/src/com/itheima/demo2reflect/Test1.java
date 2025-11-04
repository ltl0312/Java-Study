package com.itheima.demo2reflect;

import com.itheima.demo1junit.StringUtil;
import com.itheima.demo1junit.Student1;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test1 {
    @Test
    public void getClassInfo() {
        Class c1 = Dog.class;
        Class c2 = StringUtil.class;
        Class c3 = Test1.class;
        Class c4 = Student.class;
        System.out.println(c1.getName());
        System.out.println(c2.getSimpleName());
        System.out.println(c3.getSimpleName());
        System.out.println(c4.getName());
    }

    @Test
    public void getConstructorInfo() throws Exception {
        Class c1 = Dog.class;
        Class c2 = StringUtil.class;
        Class c3 = Test1.class;
        Class c4 = Student1.class;
        // 获取构造器对象。
        Constructor[] cons = c1.getDeclaredConstructors();
        Constructor[] cons2 = c2.getDeclaredConstructors();
        Constructor[] cons3 = c3.getDeclaredConstructors();
        Constructor[] cons4 = c4.getDeclaredConstructors();
        for (Constructor con : cons4) {
            System.out.println(con.getName() + "(" + con.getParameterCount() + ")");
        }

        // 获取单个构造器
        Constructor con = c1.getDeclaredConstructor(String.class);
        System.out.println(con.getName() + "(" + con.getParameterCount() + ")");
        Constructor con2 = c1.getDeclaredConstructor();
        System.out.println(con2.getName() + "(" + con2.getParameterCount() + ")");

        // 获取构造器作用依然是创建对象：创建对象。
        // 暴力反射：暴力反射可以访问私有的构造器、方法、属性。
        con2.setAccessible(true);// 绕过访问权限，直接访问！
        Dog d = (Dog) con2.newInstance();
        System.out.println(d);
        con.setAccessible(true);
        Dog d2 = (Dog) con.newInstance("小黑");
        System.out.println(d2);
    }

    @Test
    public void getFieldInfo() throws Exception {
        Class c1 = Dog.class;
        Class c2 = StringUtil.class;
        Class c3 = Test1.class;
        Class c4 = Student1.class;
        Field[] fields = c1.getDeclaredFields();
        Field[] fields2 = c2.getDeclaredFields();
        Field[] fields3 = c3.getDeclaredFields();
        Field[] fields4 = c4.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName() + "(" + field.getType().getName() + ")");
        }

        // 获取单个成员变量对象。
        Field field = c1.getDeclaredField("hobby");
        System.out.println(field.getName() + "(" + field.getType().getName() + ")");

        // 获取成员变量作用依然是访问成员变量：修改成员变量的值。
        Dog d = new Dog( "泰迪", 3);
        field.setAccessible(true);
        field.set(d, "看家");
        System.out.println(d);
    }
    @Test
    public void getMethodInfo() throws Exception {
        Class c1 = Dog.class;
        Class c2 = StringUtil.class;
        Class c3 = Test1.class;
        Class c4 = Student1.class;
        Method[] methods = c1.getDeclaredMethods();
        Method[] methods2 = c2.getDeclaredMethods();
        Method[] methods3 = c3.getDeclaredMethods();
        Method[] methods4 = c4.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName() + "(" + method.getParameterCount() + ")");
        }

        // 获取单个成员方法对象。
        Method m1 = c1.getDeclaredMethod("eat");
        System.out.println(m1.getName() + "(" + m1.getParameterCount() + ")");
        Method m2 = c1.getDeclaredMethod("eat", String.class);
        System.out.println(m2.getName() + "(" + m2.getParameterCount() + ")");

        // 获取成员方法作用依然是访问成员方法：调用成员方法。
        Dog d = new Dog( "泰迪", 3);
        m1.setAccessible(true);
        System.out.println(m1.invoke(d));//invoke唤醒某个对象中的方法
        m2.setAccessible(true);
        System.out.println(m2.invoke(d, "骨头"));//相当于d.eat("骨头");
    }
}
