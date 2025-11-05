package javaprogramming.demo22hashmap;

import java.util.HashMap;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        //目标：输入一个字符串，使用HashMap统计每个字符的出现次数。
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请输入字符串：");
            String str = sc.next();
            if (str.equals("exit")) {
                break;
            }
            HashMap hm = new HashMap();
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (hm.containsKey(c)) {
                    int count = (int) hm.get(c);
                    hm.put(c, count + 1);
                } else {
                    hm.put(c, 1);
                }
            }
            System.out.println(hm);
            System.out.println("统计结果为：");
            for (Object key : hm.keySet()) {
                System.out.println(key + "出现了" + hm.get(key) + "次");
            }
        }
    }
}
