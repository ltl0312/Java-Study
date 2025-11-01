package com.itheima.demo12inputstreamreader;

import java.io.*;

public class Demo2 {
    public static void main(String[] args) {
        //目标：使用字符输入转换流InputStreamReader解决不同编码读取乱码的问题
        //代码：UTF-8      文件：UTF-8        读取不乱码
        //代码：UTF-8      文件：GBK          读取乱码

        try (
                //先提取文件的原始字节流
                InputStream is = new FileInputStream("day03-file-io\\src\\008.txt");
                //指定字符集把原始字节流转换为字符输入流
                InputStreamReader isr = new InputStreamReader(is, "GBK");
                FileReader fr = new FileReader("day03-file-io\\src\\008.txt");
                //创建缓冲字符输入流包装低级的字符输入流
                BufferedReader br = new BufferedReader(isr);
        ) {

            //使用循环按行读取
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
