package com.itheima.method1reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Student2 {
    //姓名,年龄,身高,性别
    private String name;
    private int age;
    private double height;
    private char sex;

    public static int compareAge(Student2 o1, Student2 o2) {
        return o1.getAge() - o2.getAge();
    }

    public int compareHeight(Student2 o1, Student2 o2) {
        //按照身高比较
        return Double.compare(o1.getHeight(), o2.getHeight());
    }

}
