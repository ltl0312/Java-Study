package com.itheima.demo2map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapDemo1 {
    public static void main(String[] args) {
        //目标：认识Map集合的体系特点
        //1.创建Map集合
        //HashMap特点：无序、不重复、无索引，键值对可以是null，值不做要求
        //TreeMap特点：按照键可排序、不重复、无索引
        Map<String,Integer> map = new HashMap<>();//一行经典代码
        map.put("小王",18);
        map.put("小张",19);
        map.put("小李",20);
        map.put("小王",18);
        map.put(null, null);
        System.out.println(map);//{null=null, 小王=18, 小张=19, 小李=20}

        System.out.println("======================");

        //LinkedHashMap特点：有序、不重复、无索引，键值对可以是null，值不做要求

        Map<String,Integer> map1 = new LinkedHashMap<>();
        map1.put("小王",18);
        map1.put("小张",19);
        map1.put("小李",20);
        map1.put("小王",18);
        map1.put(null, null);
        System.out.println(map1);
    }


}
