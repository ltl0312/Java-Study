package com.itheima.demo5fileoutputstream;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileOutputStreamDemo1 {
    public static void main(String[] args) throws Exception{
        //目标：学会使用文件字节输出流
        //1.创建文件字节输出流管道与目标文件接通
        //OutputStream os = new FileOutputStream("day03-file-io\\src\\004.txt");//覆盖管道
        OutputStream os = new FileOutputStream("day03-file-io\\src\\004.txt", true);//追加管道

        //2.写入数据
        //public void write(int b) throws IOException
        os.write(97);//写入一个字节数据
        os.write('b');
        //os.write('徐');//写入一个字符数据
        os.write("\r\n".getBytes());

        //3.写一个字节数组出去
        //byte[] bytes = {97,98,99,100,101};
        byte[] bytes = "abc我爱你中国de".getBytes();
        os.write(bytes);
        os.write("\r\n".getBytes());

        //4.写一个字节数组的一部分出去
        os.write(bytes,2,3);
        os.write(bytes,0,bytes.length);

        //5.关闭流
        os.close();
    }
}
