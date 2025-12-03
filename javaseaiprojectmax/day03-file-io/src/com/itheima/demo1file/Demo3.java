package com.itheima.demo1file;

import java.io.FileInputStream;

public class Demo3 {
    public static void main(String[] args) throws Exception{
        FileInputStream in = null;
        try {
            in = new FileInputStream("test.txt");
            int b = 0;
            while (true){
                b = in.read();
                if (b == -1){
                    break;
                }
                System.out.print((char)b);
            }
            } finally {
            if (in != null) {
                in.close();
                System.out.println("关闭输入流");

            }
        }

    }
}
