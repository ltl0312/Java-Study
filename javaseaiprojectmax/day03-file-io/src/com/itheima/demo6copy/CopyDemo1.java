package com.itheima.demo6copy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyDemo1 {
    public static void main(String[] args){
        //目标：使用字节流完成文件复制
        //源文件："C:\Users\ZhuanZ\Desktop\Study--file\图片\JAVA\异常处理方案一.png"
        //目标文件："C:\Users\ZhuanZ\Desktop\异常处理方案一_复制.png"（复制过去必须带文件名，无法自动生成文件名）
        copyFile("C:\\Users\\ZhuanZ\\Desktop\\Study--file\\图片\\JAVA\\异常处理方案一.png", "C:\\Users\\ZhuanZ\\Desktop\\异常处理方案一_复制.png");
    }

    //复制文件
    public static void copyFile(String srcPath, String destPath) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            //1.创建文件字节输入流管道与源文件接通
            fis = new FileInputStream(srcPath);
            //2.创建文件字节输出流管道与目标文件接通
            fos = new FileOutputStream(destPath);
        /*while (true){
            byte[] bytes = fis.readAllBytes();
            if (bytes.length == 0){
                break;
            }
            fos.write(bytes);
            fos.flush();
            System.out.println("复制完成");
        }*/
            byte[] bytes = new byte[1024];
            while (fis.read(bytes) != -1) {
                fos.write(bytes, 0, bytes.length);
            }
            System.out.println("复制完成");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //最后一定会执行一次
            //释放资源
            try {
                if (fis != null) {
                    fis.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
