package com.itheima.arrayList;

import java.util.ArrayList;

public class ArrayListDemo1 {
    public static void main(String [] args) {
        //目标:掌握ArrayList集合的基本使用
        //创建ArrayList对象,代表一个集合容器
        ArrayList<String> list = new ArrayList<>();
        //添加数据
        list.add("hello");
        list.add("world");
        list.add("java");
        list.add("java2");
        list.add("java3");
        list.add("789");
        list.add("\n");
        list.add("\t");
        System.out.println(list);
        //查看数据
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        //遍历集合
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            System.out.println(s);
        }
        //删除数据
        list.remove(0);
        System.out.println(list);
        //修改数据
        list.set(0,"hello world");
        System.out.println(list);

    }
}
