package com.itheima.interface3;

public class Test {
    public static void main(String[] args) {
        //目标:完成接口的小案例
        //1.定义学生类
        //2.准备学生数据
        Student [] allStudents = new Student[10];
        allStudents[0] = new Student("小王",'男',55);
        allStudents[1] = new Student("小张",'女',25);
        allStudents[2] = new Student("小李",'男',30);
        allStudents[3] = new Student("小赵",'女',36);
        allStudents[4] = new Student("小周",'男',40);
        allStudents[5] = new Student("小吴",'女',51);
        allStudents[6] = new Student("小苏",'男',26);
        allStudents[7] = new Student("小陈",'女',20);
        allStudents[8] = new Student("小罗",'男',80);
        allStudents[9] = new Student("小夏",'女',28);

        //3.提供两套业务实现方案,支持灵活切换(解耦合):面向接口编程
        //  -- 定义一个接口（规范思想）：必须完成打印全班学生信息，打印平均分。(ClassDataInter)
        //  -- 定义第一套实现类，实现接口：实现打印学生信息，实现打印平均分。
        //  -- 定义第二套实现类，实现接口：实现打印学生信息（男女人数），实现打印平均分（去掉最高分和最低分）。

        ClassDataInter cdi = new ClassDataInterImpl2(allStudents);
        cdi.printAllStudentInfos();
        cdi.printAverageScore();

    }
}
