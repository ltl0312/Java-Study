package com.itheima.demo3charset;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class CharSetDemo1 {
    public static void main(String[] args) throws Exception {
        // 目标：写程序字符集的编码和解码原理
        //1.编码
        String name = "我爱你中国abc666";
        //byte[] bytes = name.getBytes();// getBytes()方法默认使用UTF-8编码
        byte[] bytes = name.getBytes("GBK");//指定GBK编码
        System.out.println(bytes.length);
        System.out.println(Arrays.toString( bytes));

        //2.解码
        String name2 = new String(bytes);
        System.out.println(name2);
        String name3 = new String(bytes,"GBK");
        System.out.println(name3);
    }
}
