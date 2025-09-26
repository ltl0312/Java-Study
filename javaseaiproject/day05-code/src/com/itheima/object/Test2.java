package com.itheima.object;

public class Test2 {
    public static void main(String[] args) {
        Student s1 = new Student();
        s1.name = "张三";
        s1.chinese = 90;
        s1.math = 80;
        System.out.println(s1.name + "的语文成绩是：" + s1.chinese);
        System.out.println(s1.name + "的数学成绩是：" + s1.math);
        System.out.println(s1.name + "的总成绩是：" + (s1.chinese + s1.math));
        System.out.println(s1.name + "的平均成绩是：" + (s1.chinese + s1.math) / 2);
        Student s2 = new Student();
        s2.name = "李四";
        s2.chinese = 80;
        s2.math = 90;
        System.out.println(s2.name + "的语文成绩是：" + s2.chinese);
        System.out.println(s2.name + "的数学成绩是：" + s2.math);
        System.out.println(s2.name + "的总成绩是：" + (s2.chinese + s2.math));
        System.out.println(s2.name + "的平均成绩是：" + (s2.chinese + s2.math) / 2);
    }
}
