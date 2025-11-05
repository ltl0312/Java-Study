package javaprogramming.demo21simulatetcp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        //目标：编写一个简单的TCP客户端和服务器程序，实现简单文本消息的传输。
        System.out.println("客户端启动....");
        Socket socket = new Socket("127.0.0.1", 9999);
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请说：");
            String msg = sc.nextLine();
            if ("exit".equals(msg)) {
                System.out.println("退出成功！");
                dos.close();
                socket.close();
                break;
            }

            dos.writeUTF(msg); // 发送数据
            dos.flush();
        }
    }
}
