package com.itheima.demo1execption;

public class ExceptionDemo4 {
    public static void main(String[] args) {
        //目标:认识自定义异常 - 运行时异常
        System.out.println("程序开始");
        try {
            saveAge(-10);
            System.out.println("保存年龄成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("错误,请输入正确的年龄");
        }
        System.out.println("程序结束");
    }

    //需求:我们公司的系统只要收到了年龄小于0岁或者大于150岁就是一个年龄非法异常
    public static void saveAge(int age)  /*throws ItheimaAgeIllegalRuntimeException*/{
        if (age < 0 || age > 150) {
            //年龄非法:抛出一个异常返回
            throw new ItheimaAgeIllegalRuntimeException("年龄非法 age 不能低于0岁不能高于150岁");
        } else {
            System.out.println("年龄合法");
            System.out.println("保存年龄:" + age);
        }
    }
}
