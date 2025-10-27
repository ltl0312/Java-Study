package com.itheima.demo4fileinputstream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileInputStreamDemo1 {
    public static void main(String[] args) throws Exception {
        //目标：掌握文件字节输入流读取文件中的字节数组到内存中来
        //1.创建文件字节输入流管道与源文件接通
        //FileInputStream fis = new FileInputStream(new File("day03-file-io\src\001.txt"));
        FileInputStream fis = new FileInputStream("day03-file-io\\src\\001.txt");//简化写法

        //2.读取文件中的字节并输出
        //定义一个变量记住每次读取的一个字节
        int b;
        //没有数据可读完毕，返回-1
        while ((b = fis.read()) != -1) {
            System.out.print((char) b);
        }
        //每次读取一个字节，性能较差，读取汉字输出一定会乱码
        fis.close();
    }
}
