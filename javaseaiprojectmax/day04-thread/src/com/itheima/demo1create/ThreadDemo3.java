package com.itheima.demo1create;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ThreadDemo3 {
    public static void main(String[] args) {
        //目标：创建多线程的方式三：使用Callable接口；优点：可以获取线程完毕后的结果

        //3.创建一个Callable接口的实现类对象
        Callable c = new MyCallable(100);
        //4.把Callable接口的实现类对象封装成一个真正的线程任务对象FutureTask对象
        /**
         * 未来任务对象的作用
         * 1.本质是一个Runnable线程任务对象，可以交给Thread线程对象来处理
         * */
        //FutureTask<String> ft = new FutureTask<>(c);
        Runnable ft = new FutureTask<>(c);//多态
        //5.把线程任务对象FutureTask作为参数交给Thread线程对象来处理
        Thread t1 = new Thread(ft);
        //6.启动线程
        t1.start();


        Callable c2 = new MyCallable(50);
        Runnable f2 = new FutureTask<>(c2);
        Thread t2 = new Thread(f2);
        t2.start();

        //获取线程任务结果
        try {
            //如果主线程发现第一个线程还没有执行完毕,会让出CPU,等第一个线程执行完毕后,才会往下执行
            System.out.println( ((FutureTask<String>)ft).get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println( ((FutureTask<String>)f2).get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



//1.定义一个线程实现类实现Callable接口

class MyCallable implements Callable<String> {
    private int n;
    public MyCallable(int n) {
        this.n = n;
    }
    //2.实现call方法，定义线程执行体
    @Override
    public String call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= n; i++) {
            sum += i;
        }
        return "子线程计算1-" + n + "结果是" + sum;
    }

}