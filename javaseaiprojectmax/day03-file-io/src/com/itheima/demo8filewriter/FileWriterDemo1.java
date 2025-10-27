package com.itheima.demo8filewriter;

import java.io.FileWriter;
import java.io.Writer;

public class FileWriterDemo1 {
    public static void main(String[] args) {
        // 目标：掌握文件字符输出流FileWriter，向文件中写入字符数据
        // 1.创建文件字符输出流与目标文件接通，指定写出的目的地，构造方法中传递文件路径或者文件对象
        try (
                //Writer fw = new FileWriter("day03-file-io\\src\\006.txt")//覆盖管道
                Writer fw = new FileWriter("day03-file-io\\src\\006.txt", true)//追加管道
        ) {
            // 2.写入数据
            // public void write(int c) throws IOException
            fw.write(97);
            fw.write('b');
            fw.write('徐');
            fw.write("\r\n");

            // public void write(char[] cbuf) throws IOException
            char[] chs = {'a', 'b', 'c', '我', '爱', '你', '中', '国', 'd', 'e'};
            fw.write(chs);
            fw.write("\r\n");
            //写出一个字符数组的一部分
            fw.write(chs, 2, 3);
            fw.write("\r\n");
            //写出字符串的一部分
            fw.write("abc我爱你中国de", 2, 3);
            fw.write("\r\n");
            fw.flush();//刷新缓冲区，将数据写出去
            //刷新后流可以继续使用
            //fw.close();//关闭流，包含了刷新，关闭后流不能再使用
            //关闭了就不用在刷新了
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
