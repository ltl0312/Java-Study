package com.itheima.demo2map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapTraverseDemo3 {
    public static void main(String[] args) {
        //目标：掌握Map集合的遍历方式一：键找值

        Map<String,Integer> map = new HashMap<>();//一行经典代码
        map.put("小王",18);
        map.put("小张",19);
        map.put("小李",20);
        map.put("小王",18);
        map.put(null, null);
        System.out.println(map);

        //1.提取Map集合的全部键到一个Set集合中
        Set<String> keys = map.keySet();
        //2.遍历Set集合，得到每一个键
        for (String key : keys) {
            //3.通过键得到对应的值
            Integer value = map.get(key);
            System.out.println(key + "=" + value);
        }
    }
}
