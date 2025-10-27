package com.itheima.demo4fileinputstream;

import java.io.FileInputStream;

public class FileInputStreamDemo2 {
    public static void main(String[] args) throws Exception {
        //目标：掌握文件字节输入流读取文件中的字节数组到内存中来
        //1.创建文件字节输入流管道与源文件接通
        //FileInputStream fis = new FileInputStream(new File("day03-file-io\src\001.txt"));
        FileInputStream fis = new FileInputStream("day03-file-io\\src\\002.txt");//简化写法

        //2.读取文件中的字节并输出：每次读取多个字节
        //定义一个字节数组记住每次读取的多个字节
        byte[] buffer = new byte[3];
        //定义一个变量记住每次读取了多少个字节
        int len;
        while ((len = fis.read(buffer)) != -1) {
            String str = new String(buffer, 0, len);
            //String str = new String(buffer);
            System.out.println(str);
        }

        //拓展：每次读取多个字节，性能比一次读取一个字节要好，因为每次读取多个字节，可以减少硬盘和内存的交互，提高性能
        //依然无法避免读取汉字时乱码：存在截断汉字字节的可能性
    }
}
