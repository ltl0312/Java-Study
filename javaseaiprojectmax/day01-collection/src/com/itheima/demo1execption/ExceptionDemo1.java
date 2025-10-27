package com.itheima.demo1execption;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ExceptionDemo1 {
    public static void main(String[] args) {
        //:目标认识异常体系,搞清楚异常的基本作用
        show();
        try {
            show1();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    //定义一个方法认识编译时异常
    public static void show1() throws ParseException {
        System.out.println("程序开始...");
        //编译时报错,编译不通过
        String str = "2025-09-27 17:58:46";
        //把字符串时间解析成Java中的日期对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = sdf.parse(str);//编译时异常,提醒程序员这里很容易出错,请您注意
        System.out.println(date);
        System.out.println("程序结束...");
    }


    //定义一个方法认识运行时的异常
    public static void show(){
        System.out.println("程序开始...");
        //运行时异常的特点:编译不报错,运行时异常,继承RuntimeException
        int[] arr = {10,20,30};
        //System.out.println(arr[3]);//运行时异常 ArrayIndexOutOfBoundsException
        //System.out.println(100/0);//运行时异常 ArithmeticException

        //空指针运行时异常:NullPointerException
//        String str = null;
//        System.out.println(str);
//        System.out.println(str.length());
//        System.out.println(str.charAt(0));

        System.out.println("程序结束...");
    }
}
