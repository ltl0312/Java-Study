package com.itheima.extendsconstructor;

public class Test3 {
    public static void main(String[] args) {
        //理解this调用兄弟构造器
        //super(...)this(...)必须写在构造器的第一行,且不能同时出现
        Student s = new Student("张三",'男',18);
        System.out.println(s);
        Student s1 = new Student("李四",'女',23);
        System.out.println(s1);
    }
}
