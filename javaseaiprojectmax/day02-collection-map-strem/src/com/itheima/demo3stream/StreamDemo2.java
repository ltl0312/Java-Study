package com.itheima.demo3stream;

import java.util.*;
import java.util.stream.Stream;

public class StreamDemo2 {
    public static void main(String[] args) {
        //目标：获取Stream流的方式
        //1.获取集合的Stream流,调用集合提供的stream()方法
        Collection<String> list = new ArrayList<>();
        Stream<String> s1 = list.stream();

        //2.Map集合,怎么拿stream流
        Map<String,Integer> map = new HashMap<>();
        //获取键流
        Stream<String> s2 = map.keySet().stream();
        //获取值流
        Stream<Integer> s3 = map.values().stream();
        //获取键值对流
        Stream<Map.Entry<String, Integer>> s4 = map.entrySet().stream();

        //3.获取数组的stream流
        String []names = {"小王","小张","小李"};
        Stream<String> s5 = Arrays.stream(names);
        System.out.println(s5.count());

        Stream<String> s6 = Stream.of("小王","小张","小李");
        System.out.println(s6.count());
    }
}
