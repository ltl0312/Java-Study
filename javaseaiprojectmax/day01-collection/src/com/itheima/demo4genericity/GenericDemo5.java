package com.itheima.demo4genericity;

import java.util.ArrayList;

public class GenericDemo5 {
    public static void main(String[] args) {
        // 目标：理解通配符和上下限。
        ArrayList<Xiaomi> xiaomis = new ArrayList<>();
        xiaomis.add(new Xiaomi());
        xiaomis.add(new Xiaomi());
        xiaomis.add(new Xiaomi());
        go(xiaomis);

        ArrayList<BYD> byds = new ArrayList<>();
        byds.add(new BYD());
        byds.add(new BYD());
        byds.add(new BYD());
        go(byds);

//        ArrayList<Dog> dogs = new ArrayList<>();
//        dogs.add(new Dog());
//        dogs.add(new Dog());
//        dogs.add(new Dog());
//        go(dogs);
    }

    // 需求：开发一个极品飞车的游戏。
    // 虽然Xiaomi和BYD是Car的子类，但是 ArrayList<Xiaomi>  ArrayList<BYD>和 ArrayList<Car> 是没有半毛钱关系！
    public static void go(ArrayList<? extends Car> cars) {
    }
}
