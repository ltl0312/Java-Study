package com.itheima.demo2genericity;

public class GenericDemo2 {
    public static void main(String[] args) {
        // 目标：学会自定义泛型类。
        // 需求：请您模拟ArrayList集合自定义一个集合MyArrayList.
        // MyArrayList<String> list = new MyArrayList<String>();
        MyArrayList<String> mlist = new MyArrayList<>(); // JDK 7开始支持的后面类型可以不写
        mlist.add("hello");
        mlist.add("world");
//        list.add(555); // 报错
        mlist.add("java");
        mlist.add("前端");

        System.out.println(mlist.remove("world"));

        System.out.println(mlist);
    }
}
