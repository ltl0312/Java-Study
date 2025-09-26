package com.itheima.innerclass3;

public class Test {
    public static void main(String[] args){
        //目标:认识匿名内部类,搞清楚其基本作用
        //匿名内部类是有名字的:外部类名$编号.class
        //匿名内部类本质是子类,并且会立即构建一个子类对象
        Animal a = new Animal(){
            @Override
            public void cry() {
                System.out.println("匿名内部类 cry()");
            }
        };
        a.cry();
    }
}
