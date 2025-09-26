package com.itheima.type;

public class TypeDemo1 {
    public static void main(String[] args) {
        //目标:认识自动类型转换,强制类型转换
        byte a = 10;

        print(a);//自动类型转换
        print2(a);//自动类型转换

        System.out.println("--------");

        int b = 10;

        //类型大的变量不能直接赋给类型小的变量,会报错
        print3((byte)b);//强制类型转换

        int m = 1500;
        byte n = (byte)m;
        System.out.println(m);
        System.out.println(n);//数据溢出
    }

    public static void print(int a)
    {
        System.out.println(a);
    }

    public static void print2(double a)
    {
        System.out.println(a);
    }

    public static void print3(byte a)
    {
        System.out.println(a);
    }
}
