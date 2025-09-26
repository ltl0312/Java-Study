package com.itheima.interface3;

public class ClassDataInterImpl2 implements ClassDataInter{
    //  -- 定义第二套实现类，实现接口：实现打印学生信息（男女人数），实现打印平均分（去掉最高分和最低分）。

    private Student[] students;//记住送进来的全部成员信息
    public ClassDataInterImpl2(Student[] students) {
        this.students = students;
    }
    @Override
    public void printAllStudentInfos() {
        int manCount = 0;
        System.out.println("所有学生信息如下:");
        for (int i = 0; i < students.length; i++) {
            Student s = students[i];
            System.out.println(s.getSex() + " " + s.getName() + " " + s.getScore());
            if (s.getSex() == '男') {
                manCount++;
            }
        }
        System.out.println("男生数量为:" + manCount);
        System.out.println("女生数量为:" + (students.length - manCount));

    }


    //实现打印平均分（去掉最高分和最低分）
    @Override
    public void printAverageScore() {
        double sum = 0;
        double max = students[0].getScore();
        double min = students[0].getScore();
        for (int i = 0; i < students.length; i++) {
            Student s = students[i];
            sum += s.getScore();
            if (s.getScore() > max) {
                max = s.getScore();
            } else if (s.getScore() < min) {
                min = s.getScore();
            }
            System.out.println("最高分是：" + max);
            System.out.println("最低分是：" + min);

        }
        sum = sum - max - min;
        System.out.println("平均分：" + sum / students.length);
    }
}
