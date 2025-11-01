package com.itheima.demo13printstream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class DataStreamDemo3 {
    public static void main(String[] args) {
        //目标：掌握特殊数据流使用
        try (
                DataInputStream dis = new DataInputStream(new FileInputStream("day03-file-io/src/dos.txt"));
        ){
            System.out.println(dis.readInt());
            System.out.println(dis.readBoolean());
            System.out.println(dis.readDouble());
            System.out.println(dis.readChar());
            System.out.println(dis.readUTF());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
