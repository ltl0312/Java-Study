package com.itheima.javabean;

public class Test {
    //目标:搞清楚实体类是什么,搞清楚其基本作用及应用场景
    //实体类的基本作用:创建它的对象,封装数据
    public static void main(String[] args) {
        Student s1 = new Student();
        s1.setName("张三");
        s1.setChinese(90);
        s1.setMath(80);
        System.out.println(s1.getName() + "的语文成绩是：" + s1.getChinese());
        System.out.println(s1.getName() + "的数学成绩是：" + s1.getMath());
        System.out.println(s1.getName() + "的总成绩是：" + (s1.getChinese() + s1.getMath()));
        System.out.println(s1.getName() + "的平均成绩是：" + (s1.getChinese() + s1.getMath()) / 2);
        Student s2 = new Student( "李四", 80, 90);
        System.out.println(s2.getName() + "的语文成绩是：" + s2.getChinese());
        System.out.println(s2.getName() + "的数学成绩是：" + s2.getMath());
        System.out.println(s2.getName() + "的总成绩是：" + (s2.getChinese() + s2.getMath()));
        System.out.println(s2.getName() + "的平均成绩是：" + (s2.getChinese() + s2.getMath()) / 2);

        StudentService ss = new StudentService(s2);
        ss.printScore();
        ss.printAvg();

    }
}
