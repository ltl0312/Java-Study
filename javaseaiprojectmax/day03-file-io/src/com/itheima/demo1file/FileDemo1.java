package com.itheima.demo1file;

import java.io.File;
import java.io.IOException;

public class FileDemo1 {
    public static void main(String[] args) throws Exception {
        //目标：创建File创建对象代表文件（文件/文件夹），搞清楚其提供的对文件继续操作的方法
        //1.创建File对象，去获取文件或文件夹信息
        File file = new File("C:\\Users\\ZhuanZ\\Desktop\\153.jpg");
        System.out.println(file.length());
        System.out.println(file);
        System.out.println(file.getName());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.isFile());
        System.out.println(file.isDirectory());
        System.out.println(file.exists());
        System.out.println(file.isHidden());
        System.out.println(file.canRead());
        System.out.println(file.canWrite());
        System.out.println(file.lastModified());

        //2.可以使用相对路径定位File对象

        //3.创建对象代表不存在的文件路径
        File file1 = new File("C:\\Users\\ZhuanZ\\Desktop\\143.jpg");
        System.out.println(file1.exists());
        System.out.println(file1.createNewFile());


        //4.创建对象代表不存在的文件夹路径
        File file2 = new File("C:\\Users\\ZhuanZ\\Desktop\\123");
        System.out.println(file2.exists());
        System.out.println(file2.mkdir());//mkdir只能创建一级文件夹

        File file3 = new File("C:\\Users\\ZhuanZ\\Desktop\\23\\456");
        System.out.println(file3.exists());
        System.out.println(file3.mkdirs());//mkdirs可以创建多级级文件夹

        //5.创建File对象代表存在的文件，然后删除它
        File file6 = new File("C:\\Users\\ZhuanZ\\Desktop\\143.jpg");
        System.out.println(file6.delete());

        //6.创建File对象代表存在的文件夹，然后删除它
        File file7 = new File("C:\\Users\\ZhuanZ\\Desktop\\123");
        System.out.println(file7.delete());//只能删除空文件夹，不能删除有文件的文件夹

        //7.可以获取某个目录下的全部一级文件名称
        File file8 = new File("C:\\Users\\ZhuanZ\\Desktop");
        String[] list = file8.list();
        int i = 0;
        for (String name : list) {
            System.out.println( ++i + ":" + name);
        }
        System.out.println("==========================");
        //8.可以获取某个目录下的全部一级文件对象
        File[] files = file8.listFiles();
        i = 0;
        for (File file9 : files) {
            System.out.println( ++i + ":" + file9);
        }
    }
}
