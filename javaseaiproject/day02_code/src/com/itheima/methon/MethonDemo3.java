package com.itheima.methon;

public class MethonDemo3 {
    public static void main(String[] args) {
        //目标:掌握在无返回值的方法中单独使用return--提前结束方法

        div(10,0);
        div(10,2);
    }

    //设计一个除法
    public static void div(int a,int b){
        if(b==0){
            System.out.println("除数不能为0");
            return;
        }
        System.out.println(a/b);
    }
}
