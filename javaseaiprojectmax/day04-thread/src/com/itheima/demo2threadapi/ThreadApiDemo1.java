package com.itheima.demo2threadapi;

public class ThreadApiDemo1 {
    public static void main(String[] args) {
        //目标：掌握线程的常用方法
        MyThread t1 = new MyThread("线程1");
        //t1.setName("线程1");//在线程开始前
        t1.start();
        System.out.println(t1.getName());//线程默认的名字是Thread-索引
        MyThread t2 = new MyThread("线程2");
        //t2.setName("线程2");
        t2.start();
        System.out.println(t2.getName());
        MyThread t3 = new MyThread("线程3");
        //t3.setName("线程3");
        t3.start();
        System.out.println(t3.getName());

        //哪个线程调用这个代码,这个代码就拿到哪个线程
        Thread m = Thread.currentThread();
        m.setName("主线程");
        System.out.println(m.getName());

        for (int i = 0; i < 10; i++) {
            System.out.println("主线程：" + i);
        }
    }
}


//1.定义一个子类继承Thread类，成为一个线程类
class MyThread extends Thread{
    public MyThread(String name) {
        super(name);
    }
    public MyThread() {
    }

    //2.重写run方法，将线程要执行的任务声明在此方法中
    @Override
    public void run() {
        //3.在run方法中编写线程要执行的任务
        for (int i = 0; i < 10; i++) {
            System.out.println("子线程：" + i);
        }
    }
}
