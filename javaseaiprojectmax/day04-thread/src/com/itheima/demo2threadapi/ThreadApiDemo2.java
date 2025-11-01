package com.itheima.demo2threadapi;

public class ThreadApiDemo2 {
    public static void main(String[] args) {
        //目标：掌握线程的sleep方法
        for (int i = 0; i < 10; i++) {
            try {
                //让当前执行的线程进入休眠状态,直到时间到了,才会继续执行
                Thread.sleep(1000);//1000ms
                System.out.println("线程1：" + i);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
