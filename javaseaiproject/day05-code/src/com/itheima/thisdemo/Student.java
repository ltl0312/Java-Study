package com.itheima.thisdemo;

public class Student {
    public void print(){
        //this是一个变量,用在方法中,用于拿到当前对象
        //哪个对象调用print方法,this就代表哪个对象
        System.out.println(this);
    }
}
