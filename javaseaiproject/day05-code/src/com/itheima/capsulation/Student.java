package com.itheima.capsulation;

public class Student {
    String name;
    private int age;
    private double chinese;
    private double math;

    public void setAge(int age){
        if(age >= 0 && age <= 120){
            this.age = age;
        }else{
            System.out.println("输入的age有误");
        }
    }

    public int getAge(){
        return age;
    }
    public void printAllScore(){
        System.out.println(name + "的总成绩是:" + " " + (chinese + math));
    }

    public void printAverageScore(){
        System.out.println(name + "的平均成绩是:" + " " + (chinese + math) / 2);
    }
}
