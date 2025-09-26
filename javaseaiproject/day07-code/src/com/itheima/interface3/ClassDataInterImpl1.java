package com.itheima.interface3;

public class ClassDataInterImpl1 implements ClassDataInter{

    private Student[] students;//记住送进来的全部成员信息
    public ClassDataInterImpl1(Student[] students) {
        this.students = students;
    }

    @Override
    public void printAllStudentInfos() {
        System.out.println("所有学生信息如下:");
        for (int i = 0; i < students.length; i++) {
            Student s = students[i];
            System.out.println(s.getSex() + " " + s.getName() + " " + s.getScore());
        }
    }

    @Override
    public void printAverageScore() {
        double sum = 0;
        for (int i = 0; i < students.length; i++) {
            Student s = students[i];
            sum += s.getScore();
        }
        System.out.println("所有学生的平均分是:" + sum / students.length);
    }
}
