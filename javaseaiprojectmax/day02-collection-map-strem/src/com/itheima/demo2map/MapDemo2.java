package com.itheima.demo2map;

import java.util.HashMap;
import java.util.Map;

public class MapDemo2 {
    public static void main(String[] args) {
        //目标：掌握Map的常用方法


        Map<String,Integer> map = new HashMap<>();//一行经典代码
        map.put("小王",18);
        map.put("小张",19);
        map.put("小李",20);
        map.put("小王",18);
        map.put(null, null);
        System.out.println(map);

        //写代码演示常用方法
        System.out.println(map.size());
        System.out.println(map.isEmpty());
        System.out.println(map.containsKey("小王"));
        System.out.println(map.containsValue(18));
        System.out.println(map.get("小王"));
        System.out.println(map.remove("小王"));
        System.out.println(map.remove("小王"));
        System.out.println(map.put("小王",18));
    }
}
