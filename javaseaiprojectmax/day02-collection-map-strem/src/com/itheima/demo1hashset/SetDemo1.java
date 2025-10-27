package com.itheima.demo1hashset;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SetDemo1 {
    public static void main(String[] args) {
        //目标:认识Set家族集合的特点
        //1.创建一个Set集合(HashSet):无序、不可重复、无索引
        //LinkedHashSet: 有序、不可重复、无索引
        //Set<String> set = new HashSet<>(); //经典代码
        //HashSet集合去重必须要重写hashCode()和equals()方法
        Set<String> set = new LinkedHashSet<>();
        set.add("Java");
        set.add("Java");
        set.add("C");
        set.add("C++");
        set.add("鸿蒙");
        set.add("鸿蒙");
        set.add("电商设计");
        set.add("电商设计");
        set.add("新媒体");
        set.add("大数据");
        System.out.println(set);

        //2.创建一个TreeSet集合:排序(默认升序)、不可重复、无索引
        //排序时候必须继承Comparable接口，重写compareTo()方法
        //或者创建TreeSet集合的时候传入Comparator接口的实现类对象
        Set<Double> set1 = new TreeSet<>();
        set1.add(99.9);
        set1.add(25.3);
        set1.add(789.0);
        set1.add(4562.0);
        set1.add(6.32);
        set1.add(45.123);
        set1.add(23.0);
        System.out.println(set1);
    }
}
