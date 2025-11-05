package javaprogramming.demo21simulatetcp;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;

public class ServerReader extends Thread{
    private Socket socket;
    public ServerReader(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            while (true) {
                String msg = dis.readUTF(); // 等待读取客户端发送的数据
                System.out.println("收到的客户端msg=" + msg);
                System.out.println("客户端的ip=" + socket.getInetAddress().getHostAddress());
                System.out.println("客户端的端口=" + socket.getPort());
                System.out.println("--------------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("客户端下线了："+ socket.getInetAddress().getHostAddress());
        }
    }
}
