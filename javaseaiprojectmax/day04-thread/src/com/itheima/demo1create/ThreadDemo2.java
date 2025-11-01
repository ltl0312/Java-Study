package com.itheima.demo1create;

public class ThreadDemo2 {
    public static void main(String[] args) {
        //目标：创建多线程的方式二：实现Runnable接口
        //3.创建线程任务对象代表一个线程任务
        Runnable r = new RunnableImpl();
        //4.把线程任务对象交给线程对象来处理
        Thread t1 = new Thread(r, "线程1");//设置名字
        t1.start();

        //主线程
        for (int i = 0; i < 10; i++) {
            System.out.println("主线程正在执行..."+i);
        }
    }
}


//1.定义一个线程任务类实现Runnable接口
class RunnableImpl implements Runnable{
    //2.重写run方法，将线程任务声明在此方法中
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("线程任务正在执行..."+i);
        }
    }
}
