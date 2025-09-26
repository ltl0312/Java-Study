package com.itheima.innerclass3;

import jdk.jfr.DataAmount;
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
}
