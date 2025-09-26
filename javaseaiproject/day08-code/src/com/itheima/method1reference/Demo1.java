package com.itheima.method1reference;

import java.util.Arrays;

public class Demo1 {
    public static void main(String[] args) {
        //目标:静态方法引用,演示一个场景
        test();
    }
    public static void test() {
        Student2[] students = new Student2[6];
        students[0] = new Student2("小王", 18, 170, '男');
        students[1] = new Student2("小张", 19, 160, '女');
        students[2] = new Student2("小李", 17, 180, '男');
        students[3] = new Student2("小赵", 16, 170, '女');
        students[4] = new Student2("小孙", 18, 160, '男');
        students[5] = new Student2("小周", 17, 180, '女');

//        Arrays.sort(students, (o1, o2) -> o1.getAge() - o2.getAge());
//
//        Arrays.sort(students, (o1, o2) -> Student2.compareAge(o1, o2));

        //静态方法引用: 类名::静态方法名(只有"->"前后参数一致时才能简化)
        Arrays.sort(students, Student2::compareAge);


        //遍历输出
        for (int i = 0; i < students.length; i++) {
            Student2 s = students[i];
            System.out.println(s);
        }
    }
}
