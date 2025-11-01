package com.itheima.demo1create;

public class ThreadDemo2_2 {
    public static void main(String[] args) {
        //目标：创建线程方式二：使用Runnable接口的匿名内部类来创建
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("子线程正在执行..."+i);
                }
            }
        };
        //4.把线程任务对象交给线程对象来处理
        Thread t1 = new Thread(r);
        t1.start();

        //主线程
        for (int i = 0; i < 10; i++) {
            System.out.println("主线程正在执行..."+i);
        }
    }
}
