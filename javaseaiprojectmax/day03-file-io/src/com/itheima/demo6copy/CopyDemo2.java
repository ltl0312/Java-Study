package com.itheima.demo6copy;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CopyDemo2 {
    public static void main(String[] args){
        //目标：掌握资源的新方式：try-with-resources
        //源文件："C:\Users\ZhuanZ\Desktop\Study--file\图片\JAVA\异常处理方案一.png"
        //目标文件："C:\Users\ZhuanZ\Desktop\异常处理方案一_复制.png"（复制过去必须带文件名，无法自动生成文件名）
        copyFile("C:\\Users\\ZhuanZ\\Desktop\\Study--file\\图片\\JAVA\\异常处理方案一.png", "C:\\Users\\ZhuanZ\\Desktop\\异常处理方案一_复制.png");
    }

    //复制文件
    public static void copyFile(String srcPath, String destPath) {
        try (
                //这里只能放置资源对象，用完后会自动调用close方法关闭
                //1.创建文件字节输入流管道与源文件接通
                FileInputStream fis = new FileInputStream(srcPath);
                //2.创建文件字节输出流管道与目标文件接通
                FileOutputStream fos = new FileOutputStream(destPath);
                MyConn conn = new MyConn();//自定义资源对象，会自动调用close方法关闭
        ){
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fis.read(bytes)) != -1) {
                fos.write(bytes, 0, len); // 只写入实际读取到的数据长度
            }
            System.out.println("复制完成");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 将 MyConn 移动为成员内部类
    static class MyConn implements AutoCloseable {
        @Override
        public void close() throws Exception {
            System.out.println("关闭连接");
        }
    }
}

/*package com.itheima.demo6copy;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CopyDemo2 {
    public static void main(String[] args){
        //目标：掌握资源的新方式：try-with-resources
        //源文件："C:\Users\ZhuanZ\Desktop\Study--file\图片\JAVA\异常处理方案一.png"
        //目标文件："C:\Users\ZhuanZ\Desktop\异常处理方案一_复制.png"（复制过去必须带文件名，无法自动生成文件名）
        copyFile("C:\\Users\\ZhuanZ\\Desktop\\Study--file\\图片\\JAVA\\异常处理方案一.png", "C:\\Users\\ZhuanZ\\Desktop\\异常处理方案一_复制.png");
    }

    //复制文件
    public static void copyFile(String srcPath, String destPath) {
        try (
                //这里只能放置资源对象，用完后会自动调用close方法关闭
                //1.创建文件字节输入流管道与源文件接通
                FileInputStream fis = new FileInputStream(srcPath);
                //2.创建文件字节输出流管道与目标文件接通
                FileOutputStream fos = new FileOutputStream(destPath);
                MyConn conn = new MyConn();//自定义资源对象，会自动调用close方法关闭
                ){

//        while (true){
//            byte[] bytes = fis.readAllBytes();
//            if (bytes.length == 0){
//                break;
//            }
//            fos.write(bytes);
//            fos.flush();
//            System.out.println("复制完成");
//        }
byte[] bytes = new byte[1024];
            while (fis.read(bytes) != -1) {
        fos.write(bytes, 0, bytes.length);
            }
                    System.out.println("复制完成");

        } catch (Exception e) {
        e.printStackTrace();
        }
                }


                }
class MyConn implements AutoCloseable{
    @Override
    public void close() throws Exception {
        System.out.println("关闭连接");
    }
}
}
*/