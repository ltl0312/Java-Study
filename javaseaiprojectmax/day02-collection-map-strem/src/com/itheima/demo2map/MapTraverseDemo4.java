package com.itheima.demo2map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapTraverseDemo4 {
    public static void main(String[] args) {
        //目标：掌握集合的遍历方式二：键值对

        Map<String,Integer> map = new HashMap<>();//一行经典代码
        map.put("小王",18);
        map.put("小张",19);
        map.put("小李",20);
        map.put("小王",18);
        map.put(null, null);
        System.out.println(map);


        //1.把Map集合转化Set集合，里面的类型都是键值对类型（Map.Entry<String, Integer>）
        /**
         * map = {null=null, 小王=18, 小张=19, 小李=20}
         * ↓
         * map.entrySet()
         * ↓
         * Set<Map.Entry<String, Integer>> = [(null=null), (小王=18), (小张=19), (小李=20)]
         *
         * */
        Set<Map.Entry<String, Integer>> entries = map.entrySet();

        for (Map.Entry<String, Integer> entry : entries) {
            //2.获取键值对中的键
            String key = entry.getKey();
            //3.获取键值对中的值
            Integer value = entry.getValue();
            System.out.println(key + "=" + value);
        }
    }
}
