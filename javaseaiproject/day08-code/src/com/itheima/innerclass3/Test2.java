package com.itheima.innerclass3;

public class Test2 {
    public static void main(String[] args){
        //目标:搞清楚匿名内部类的使用形式,通常可以作为一个对象传输给方法使用
        //需求:学生,老师都要参加游泳比赛
        //Swim s1 = new Student();
        Swim s1 = new Swim() {
            @Override
            public void swimming() {
                System.out.println("学生蛙泳");
            }
        };
        play(s1);
        //Swim s2 = new Teacher();
        Swim s2 = new Swim() {
            @Override
            public void swimming() {
                System.out.println("老师狗刨式");
            }
        };
        play(s2);
    }
    //设计一个方法,可以接收学生和老师开始比赛
    public static void play(Swim s){
        System.out.println("开始比赛...");
        s.swimming();
        System.out.println("比赛结束...");
    }
}


class Student implements Swim{
    public void swimming(){
        System.out.println("学生哇泳");
    }
}
class Teacher implements Swim{
    public void swimming(){
        System.out.println("老师狗刨式");
    }
}

interface Swim{
    public abstract void swimming();
}
