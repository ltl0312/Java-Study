package javaprogramming.demo21simulatetcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        System.out.println("服务端启动了...");
        ServerSocket ss = new ServerSocket(9999);

        while (true) {
            Socket socket = ss.accept();
            System.out.println("一个客户端上线了：" + socket.getInetAddress().getHostAddress());
            new ServerReader(socket).start();
        }
    }
}
