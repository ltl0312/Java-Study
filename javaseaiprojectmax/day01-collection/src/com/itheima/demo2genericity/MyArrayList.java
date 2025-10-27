package com.itheima.demo2genericity;

import java.util.ArrayList;

//泛型类
public class MyArrayList <E>{

    private ArrayList list = new ArrayList();

    public boolean add(E e){
//        System.out.println(e);
        return true;
    }

    public void remove(E e) {
        System.out.println(e);
        System.out.println("删除成功");
    }
}
