package javaprogramming.demo26httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleHttpServer1 {
    public static void main(String[] args) throws IOException {
        // 创建HttpServer实例，绑定到8080端口
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        // 为根路径（"/"）设置处理器
        server.createContext("/", new MyHandler());
        // 设置执行器为null，表示使用默认的单线程执行器
        server.setExecutor(null);
        // 启动服务器
        server.start();
        System.out.println("HTTP服务器已启动，监听端口8080");
        System.out.println("请访问 http://localhost:8080/");

    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                // 设置响应头，状态码200表示成功
                exchange.sendResponseHeaders(200, "Hello, World!".length());
                // 获取输出流以发送响应
                OutputStream os = exchange.getResponseBody();
                os.write("Hello, World!".getBytes());
                os.close();
            } else {
                // 如果不是GET请求，返回405 Method Not Allowed
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }
}