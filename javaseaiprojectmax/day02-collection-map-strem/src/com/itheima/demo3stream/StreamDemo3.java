package com.itheima.demo3stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StreamDemo3 {
    public static void main(String[] args) {
        //目标：掌握Stream流提供的常用的中间方法,对流上的数据进行处理(返回新流,支持链式编程)
        List<String> list = new ArrayList<>();
        list.add("张无忌");
        list.add("周芷若");
        list.add("赵敏");
        list.add("张强");
        list.add("张三丰");
        list.add("张翠山");

        //1.过滤方法
        list.stream()
                .filter(name -> name.startsWith("张"))
                .filter(name -> name.length() == 3)
                .forEach(name -> System.out.println(name));

        //2.排序方法
        List<Double> scores = new ArrayList<>();
        scores.add(9.5);
        scores.add(8.5);
        scores.add(7.5);
        scores.add(6.5);
        scores.add(5.5);
        scores.add(4.5);

        scores.stream()
                .sorted()
                .forEach(score -> System.out.println(score));

        //3.映射方法
        list.stream()
                .map(name -> name.length())
                .forEach(length -> System.out.println(length));

        //4.合并流
        Stream<String> s1 = Stream.of("小王","小张","小李");
        Stream<Double> s2 = Stream.of(12.2,45.5,324.32,23.5,23.5);
        Stream<Object> s3 = Stream.concat(s1,s2);
        s3.forEach(obj -> System.out.println(obj));

    }
}
