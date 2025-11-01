package com.itheima.demo12inputstreamreader;

import java.io.BufferedReader;
import java.io.FileReader;

public class Demo1 {
    public static void main(String[] args) {
        //目标：演示一个问题：不同编码读取乱码的问题
        //代码：UTF-8      文件：UTF-8        读取不乱码
        //代码：UTF-8      文件：GBK          读取乱码

        try (
                FileReader fr = new FileReader("day03-file-io\\src\\008 .txt");
                //创建缓冲字符输入流包装低级的字符输入流
                BufferedReader br = new BufferedReader(fr);
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
