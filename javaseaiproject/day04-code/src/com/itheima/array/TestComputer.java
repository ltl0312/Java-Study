package com.itheima.array;

public class TestComputer {
    public static void main(String[] args) {
        Computer p = new Computer("HW", "i7", "8G", "13*14");
        System.out.println(p.brand); // default属性：只能被本包中类访问
        System.out.println(p.screen); // public属性： 可以任何其他类访问
// System.out.println(p.cpu); // private属性：只能在Computer类中访问，不能被其他类访问
    }
}
