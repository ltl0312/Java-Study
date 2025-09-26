package com.itheima.interface1;
// 接口：使用interface关键字定义的
public interface A {
    // JDK 8之前，接口中只能定义常量和抽象方法。
    // 1、常量：接口中定义常量可以省略public static final不写，默认会加上去。
    String SCHOOL_NAME = "黑马程序员";
    // public static final String SCHOOL_NAME2 = "黑马程序员";

    // 2、抽象方法：接口中定义抽象方法可以省略public abstract不写，默认会加上去。
    // public abstract void run();
    void run();
    public abstract String go();
}