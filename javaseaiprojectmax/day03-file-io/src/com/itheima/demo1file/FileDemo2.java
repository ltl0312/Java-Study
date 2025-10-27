package com.itheima.demo1file;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;

public class FileDemo2 {
    public static void main(String[] args) {
        // 目标：掌握File遍历一级文件对象的操作
        //空文件时输出null
        File dir = new File("C:\\Users\\ZhuanZ\\Desktop\\223");
        String[] files = dir.list();
        System.out.println(Arrays.toString(files));
        //空文件夹时输出空数组
        File dir2 = new File("C:\\Users\\ZhuanZ\\Desktop\\23\\456");
        File[] files2 = dir2.listFiles();
        System.out.println(Arrays.toString(files2));
    }
}
