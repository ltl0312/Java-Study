package com.itheima.demo13printstream;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

public class PrintStreamDemo1 {
    public static void main(String[] args) {
        //目标：掌握打印流的使用
        try (
                //PrintStream ps = new PrintStream("day03-file-io/src/ps.txt");//字节输出流
                PrintStream ps = new PrintStream(new FileOutputStream("day03-file-io/src/ps.txt", true));//追加管道
                //PrintWriter ps = new PrintWriter("day03-file-io/src/ps.txt");//字符输出流
                ){
            ps.println(97);
            ps.println(true);
            ps.println(3.14);
            ps.println('a');
            ps.println("hello world");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
