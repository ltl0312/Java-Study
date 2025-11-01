package com.itheima.demo3udp2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClientDemo1 {
    public static void main(String[] args) throws Exception {
        System.out.println("客户端启动了...");
        //目标：完成UDP通信的多发多收：客户端开发
        //1.创建发送端对象
        DatagramSocket sender = new DatagramSocket();
        Scanner sc = new Scanner(System.in);
        while (true) {
            //2.创建数据包对象封装要发送的数据
            System.out.println("请说：");
            //String msg = sc.next();//不接收空格
            String msg = sc.nextLine();//接收一整行

            //如果用户输入的是exit,则退出
            if ("exit".equals(msg)) {
                System.out.println("客户端退出...");
                sender.close();
                break;
            }
            byte[] bys = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(bys, bys.length, InetAddress.getLocalHost(), 10086);

            //3.让发送端对象发送数据包是数据
            sender.send(packet);
        }
    }
}
