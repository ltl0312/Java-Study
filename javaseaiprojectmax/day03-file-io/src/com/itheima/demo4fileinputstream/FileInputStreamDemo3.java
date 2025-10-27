package com.itheima.demo4fileinputstream;

import java.io.FileInputStream;

public class FileInputStreamDemo3 {
    public static void main(String[] args) throws Exception {
        //目标：掌握文件字节输入流读取文件中的字节数组到内存中来
        //1.创建文件字节输入流管道与源文件接通
        //FileInputStream fis = new FileInputStream(new File("day03-file-io\src\001.txt"));
        FileInputStream fis = new FileInputStream("day03-file-io\\src\\003.txt");//简化写法

        //2.一次性读完全部字节
        byte[] bytes = fis.readAllBytes();
        System.out.println(new String(bytes));

    }
}
