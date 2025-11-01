package com.itheima.demo1create;

public class ThreadDemo1 {
    //main方法本身是由一条主线程推荐执行的
    public static void main(String[] args) {
        //目标：认识多线程，掌握创建线程的方式一：继承Thread类
        //4.创建线程对象，调用start方法启动线程
        MyThread t1 = new MyThread();
        //5.调用start方法启动线程
        t1.start();
        MyThread t2 = new MyThread();
        t2.start();
        MyThread t3 = new MyThread();
        t3.start();

        for (int i = 0; i < 10; i++) {
            System.out.println("主线程：" + i);
        }
    }
}


//1.定义一个子类继承Thread类，成为一个线程类
class MyThread extends Thread{
    //2.重写run方法，将线程要执行的任务声明在此方法中
    @Override
    public void run() {
        //3.在run方法中编写线程要执行的任务
        for (int i = 0; i < 10; i++) {
            System.out.println("子线程：" + i);
        }
    }
}
