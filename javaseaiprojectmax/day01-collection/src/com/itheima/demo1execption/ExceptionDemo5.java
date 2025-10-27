package com.itheima.demo1execption;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ExceptionDemo5 {
    public static void main(String[] args) {
        //目标:掌握异常的处理方案一:底层异常都抛给最外层调用者,最外层捕获异常,记录异常,响应合适信息给用户观看
        System.out.println("程序开始...");
        try {
            show1();
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("程序结束...");
    }

    public static void show1() throws Exception {
        //编译时报错,编译不通过
        String str = "2025-09-27 17:58:46";
        //把字符串时间解析成Java中的日期对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = sdf.parse(str);//编译时异常,提醒程序员这里很容易出错,请您注意
        System.out.println(date);
        InputStream is = new FileInputStream("D:/a.txt");

    }
}
