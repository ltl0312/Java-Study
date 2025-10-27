package com.itheima.demo7fileReader;

import java.io.FileReader;

public class FileReaderDemo1 {
    public static void main(String[] args) {
        // 目标：掌握文件字符输入流FileReader，读取字符到程序中来
        // 1.创建文件字符输入流与源文件接通，构造方法中传递文件路径或者文件对象
        // FileReader fr = new FileReader("day03-file-io\\src\\001.txt");
        int ch = 0;
        try (
                FileReader fr = new FileReader("day03-file-io\\src\\003.txt")
        ) {
            // 2.定义一个字符数组，每次读多个字符
            char[] chs = new char[1024];
            int len;//记录每次读取的字符个数
            while ((len = fr.read(chs)) != -1) {
                // 3.把字符数组转换成字符串输出
                System.out.print(new String(chs, 0, len));
                //拓展：文件字符输入流每次读取多个字符，性能较好，而且读取中文
                //是按照字符读取不会乱码，这是读取中文很好的方案
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println((char)ch);
    }
}
