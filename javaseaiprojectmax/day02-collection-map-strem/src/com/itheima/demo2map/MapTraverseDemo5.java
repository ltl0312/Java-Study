package com.itheima.demo2map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class MapTraverseDemo5 {
    public static void main(String[] args) {
        //目标：掌握集合的遍历方式三：lambda

        Map<String,Integer> map = new HashMap<>();//一行经典代码
        map.put("小王",18);
        map.put("小张",19);
        map.put("小李",20);
        map.put("小王",18);
        map.put(null, null);
        System.out.println(map);

        //1.直接调用Map集合的forEach方法完成遍历
        map.forEach(new BiConsumer<String, Integer>() {
            @Override
            public void accept(String s, Integer integer) {
                System.out.println(s+"="+integer);
            }
        });

        // map.forEach((k,v)->{System.out.println(k+"="+v);});


    }
}
