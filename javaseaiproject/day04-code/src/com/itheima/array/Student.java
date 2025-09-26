package com.itheima.array;

public class Student{
    public String name;
    public String gender;
    public int age;
    public double score;
    public static String classRoom = "Bit306";
    public Student(String name, String gender, int age, double score) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.score = score;
    }
    // ...
    public static void main(String[] args) {
// 静态成员变量可以直接通过类名访问
        System.out.println(Student.classRoom);
        Student s1 = new Student("Li leilei", "男", 18, 3.8);
        Student s2 = new Student("Han MeiMei", "女", 19, 4.0);
        Student s3 = new Student("Jim", "男", 18, 2.6);
// 也可以通过对象访问：但是classRoom是三个对象共享的
        System.out.println(s1.classRoom);
        System.out.println(s2.classRoom);
        System.out.println(s3.classRoom);
    }
}
