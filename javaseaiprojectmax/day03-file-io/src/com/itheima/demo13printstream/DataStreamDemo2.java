package com.itheima.demo13printstream;

import java.io.DataOutputStream;
import java.io.FileOutputStream;

public class DataStreamDemo2 {
    public static void main(String[] args) {
        //目标：掌握特殊数据流使用
        try (
                DataOutputStream dos = new DataOutputStream(new FileOutputStream("day03-file-io/src/dos.txt"));
        ){
            dos.writeInt(100);
            dos.writeBoolean(true);
            dos.writeDouble(3.14);
            dos.writeChar('a');
            dos.writeUTF("hello world");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
