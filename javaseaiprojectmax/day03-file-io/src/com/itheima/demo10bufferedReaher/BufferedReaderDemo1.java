package com.itheima.demo10bufferedReaher;

import java.io.BufferedReader;
import java.io.FileReader;

public class BufferedReaderDemo1 {
    public static void main(String[] args) {
        // 目标：掌握缓冲字符输入流BufferedReader读取字符内容：性能提升了，多了按照行读取文本的能力
        int ch = 0;
        try (
                FileReader fr = new FileReader("day03-file-io\\src\\007.txt");
                //创建缓冲字符输入流包装低级的字符输入流
                BufferedReader br = new BufferedReader(fr);
        ) {
//            // 2.定义一个字符数组，每次读多个字符
//            char[] chs = new char[1024];
//            int len;//记录每次读取的字符个数
//            while ((len = br.read(chs)) != -1) {
//                // 3.把字符数组转换成字符串输出
//                System.out.print(new String(chs, 0, len));
//                //拓展：文件字符输入流每次读取多个字符，性能较好，而且读取中文
//                //是按照字符读取不会乱码，这是读取中文很好的方案
//            }

//            System.out.println(br.readLine());
//            System.out.println(br.readLine());
//            System.out.println(br.readLine());
//            System.out.println(br.readLine());

            //使用循环按行读取
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println((char)ch);
    }
}
