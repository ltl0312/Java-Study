package com.itheima.demo2threadapi;

public class ThreadApiDemo3 {
    public static void main(String[] args) {
        //目标：掌握线程的join方法:线程插队:让调用这个方法的线程先执行完
        MyThread2 t1 = new MyThread2();
        t1.start();

        for (int i = 1; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "线程输出:" + i);
            if (i == 5) {
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


class MyThread2 extends Thread{

    @Override
    public void run() {
        for (int i = 1; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "子线程输出:" + i);
        }
    }
}