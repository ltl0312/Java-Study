package com.itheima.demo3udp2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServerDemo2 {
    public static void main(String[] args) throws Exception {
        System.out.println("服务端启动了...");
        //目标：完成UDP通信的多发多收：服务端开发
        //1.创建一个接收端对象，注册端口
        DatagramSocket receiver = new DatagramSocket(10086);
        //2.创建一个数据包对象负责接收数据
        byte[] bys = new byte[1024 * 64];
        DatagramPacket packet = new DatagramPacket(bys, bys.length);
        while (true) {
            //3.调用方法，接收数据，将数据封装到字节数组中去
            receiver.receive(packet);//阻塞(等待)式接收数据

            //4.解析数据
            //获取当前数据的长度
            int length = packet.getLength();
            String data = new String(bys, 0, length);
            System.out.println("服务端收到了:" + data);

            //5.获取发送端的ip和端口
            String ip = packet.getAddress().getHostAddress();
            int port = packet.getPort();
            System.out.println("发送端的ip:" + ip + "发送端的端口:" + port);
            System.out.println("-----------------------------------------");
        }
    }
}
