package com.itheima.extends1demo;

public class Test {
    public static void main(String[] args) {
        //认识继承
        Teacher t = new Teacher();
        t.setName("小王");
        t.setSex('男');
        t.setSkill("教师");
        System.out.println(t.getName());
        System.out.println(t.getSex());
        System.out.println(t.getSkill());
        System.out.println("--------");
        Consultant c = new Consultant();
        c.setName("小王");
        c.setSex('男');
        c.setSkill("咨询师");
        c.setNumber(1);
        System.out.println(c.getName());
        System.out.println(c.getSex());
        System.out.println(c.getSkill());
        System.out.println(c.getNumber());
        System.out.println("--------");
        Poeple p = new Poeple();
        p.setName("小王");
        p.setSex('男');
        System.out.println(p.getName());
        System.out.println(p.getSex());
        System.out.println("--------");
    }
}
