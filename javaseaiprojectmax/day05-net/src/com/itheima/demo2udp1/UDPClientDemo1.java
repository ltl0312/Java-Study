package com.itheima.demo2udp1;

import java.net.*;
import java.nio.channels.DatagramChannel;

public class UDPClientDemo1 {
    public static void main(String[] args) throws Exception {
        System.out.println("客户端启动了...");
        //目标：完成UDP通信的一发一收：客户端开发
        //1.创建发送端对象
        DatagramSocket sender = new DatagramSocket();
        //2.创建数据包对象封装要发送的数据
        byte[] bys = "hello,UDP,吃了吗".getBytes();
        /**
         * 参数一：发送的数据，字节数组
         * 参数二：字节数组的长度
         * 参数三：指定接收端的ip地址
         * 参数四：指定接收端的端口号
         * */
        DatagramPacket packet = new DatagramPacket(bys, bys.length, InetAddress.getLocalHost(), 10086);

        //3.让发送端对象发送数据包是数据
        sender.send(packet);

        sender.close();
    }
}
