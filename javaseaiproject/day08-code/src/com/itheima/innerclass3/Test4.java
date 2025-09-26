package com.itheima.innerclass3;

import java.util.Arrays;
import java.util.Comparator;

public class Test4 {
    public static void main(String [] args) {
        //目标:完成给数组排序,理解其中匿名内部类的用法
        //准备一个学生类型的数组,存放六个学生
        Student2[] students = new Student2[6];
        students[0] = new Student2("小王", 18, 170, '男');
        students[1] = new Student2("小张", 19, 160, '女');
        students[2] = new Student2("小李", 17, 180, '男');
        students[3] = new Student2("小赵", 16, 170, '女');
        students[4] = new Student2("小孙", 18, 160, '男');
        students[5] = new Student2("小周", 17, 180, '女');


        //按年龄升序排序
        Arrays.sort(students, new Comparator<Student2>() {
            @Override
            public int compare(Student2 o1, Student2 o2) {
                return o1.getAge() - o2.getAge();
            }
        });


        //遍历输出
        for (int i = 0; i < students.length; i++) {
            Student2 s = students[i];
            System.out.println(s);
        }

    }
}
