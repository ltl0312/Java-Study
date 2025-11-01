package com.itheima.demo14commonsio;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class CommonsIoDemo1 {
    public static void main(String[] args) throws Exception {
        //目标：掌握commons-io使用
        FileUtils.copyFile(new File( "day03-file-io/src/dos.txt"), new File( "day03-file-io/src/dos1.txt"));

        FileUtils.deleteDirectory(new File("day03-file-io/src/001.txt"));
    }
}
