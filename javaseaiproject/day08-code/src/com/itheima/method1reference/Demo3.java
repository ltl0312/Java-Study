package com.itheima.method1reference;

import java.util.Arrays;
import java.util.Comparator;

public class Demo3 {
    public static void main(String[] args) {
        //目标:特定类型的方法引用,演示一个场景
        //需求:有一个字符串数组,里面有一些人的英文名字,请按照名字首字母升序排序
        String[] names = {"tom", "jeck", "mike", "jack", "kate", "lucy", "Tome", "Omla", "Maqd", "Lopka", "zip"};

        //把这个数组进行排序:升序
        //Arrays.sort(names);
        //忽略首字母的大小写进行排序
//        Arrays.sort(names, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o1.compareToIgnoreCase(o2);
//            }
//        });

        Arrays.sort(names, (String o1, String o2) -> o1.compareToIgnoreCase(o2));

        //特定类型的方法引用:类名::方法名
        //如果某个Lambda表达式里只是调用一个特定类型的实例方法,并且前面参数列表中的第一个参数是作为方法的主调,后面的所有参数都是作为该实例方法的入参的,则此时就可以使用特定类型的方法引用
        Arrays.sort(names, String::compareToIgnoreCase);

        System.out.println(Arrays.toString( names));
    }
}
