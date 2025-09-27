package com.itheima.demo1exception;

public class ExceptionDemo3 {
    public static void main(String[] args) {
        // 目标：认识自定义异常。
        System.out.println("程序开始。。。。");
        try {
            saveAge(300);
            System.out.println("成功了!");
        } catch (ItheimaAgeIllegalException e) {
            e.printStackTrace();
            System.out.println("失败了！");
        }
        System.out.println("程序结束。。。。");
    }

    // 需求：我们公司的系统只要收到了年龄小于1岁或者大于200岁就是一个年龄非法异常。
    public static void saveAge(int age) throws ItheimaAgeIllegalException {
        if(age < 1 || age > 200){
            // 年龄非法；抛出去一个异常。
            throw new ItheimaAgeIllegalException("年龄非法 age 不能低于1岁不能高于200岁");
        }else {
            System.out.println("年龄合法");
            System.out.println("保存年龄：" + age);
        }
    }
}
