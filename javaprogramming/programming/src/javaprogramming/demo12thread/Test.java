package javaprogramming.demo12thread;

public class Test {
    public static void main(String[] args) {
        //目标：创建一个简单的多线程程序，两个线程分别输出1到10和A到J。
        //一个子线程一个主线程
        MyThread myThread = new MyThread();
        myThread.start();
        for (int i = 0; i < 10; i++) {
            System.out.println((char)('A' + i));
        }

    }
}
