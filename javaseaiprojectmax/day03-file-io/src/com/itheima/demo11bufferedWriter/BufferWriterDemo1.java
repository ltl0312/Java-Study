package com.itheima.demo11bufferedWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;

public class BufferWriterDemo1 {
    public static void main(String[] args) {
        // 目标：掌握缓冲字符输出流FileWriter：提升了字符输出流的写字符性能，多了换行功能
        // 1.创建文件字符输出流与目标文件接通，指定写出的目的地，构造方法中传递文件路径或者文件对象
        try (
                //Writer fw = new FileWriter("day03-file-io\\src\\006.txt")//覆盖管道
                Writer fw = new FileWriter("day03-file-io\\src\\006.txt", true);//追加管道

                //创建缓冲字符输出流对象，把低级的字符输出流对象作为构造方法参数传递给缓冲字符输出流对象
                BufferedWriter bw = new BufferedWriter(fw);
        ) {
            // 2.写入数据
            // public void write(int c) throws IOException
            bw.write(97);
            bw.write('b');
            bw.write('徐');
            bw.write("\r\n");

            // public void write(char[] cbuf) throws IOException
            char[] chs = {'a', 'b', 'c', '我', '爱', '你', '中', '国', 'd', 'e'};
            bw.write(chs);
            bw.write("\r\n");
            //写出一个字符数组的一部分
            bw.write(chs, 2, 3);
            bw.write("\r\n");
            //写出字符串的一部分
            bw.write("abc我爱你中国de", 2, 3);
            bw.write("\r\n");
            bw.flush();//刷新缓冲区，将数据写出去
            //刷新后流可以继续使用
            //fw.close();//关闭流，包含了刷新，关闭后流不能再使用
            //关闭了就不用在刷新了
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
