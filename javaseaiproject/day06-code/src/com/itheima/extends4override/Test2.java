package com.itheima.extends4override;

public class Test2 {
    public static void main(String[] args) {
        //方法重写常见的应用场景:子类重写Object的toString方法,以便返回对象的内容

        Student s = new Student("小王",'男',18);
        System.out.println(s);
        //直接输出对象,会默认调用Object的toString方法(可以省略不写),返回对象的地址信息
        //输出对象的地址实际上是没有什么意义的,开发中更希望输出对象时看对象的内容信息,所以一般重写toString方法,返回对象的内容
        //以便以后输出对象时默认就近调用子类重写的toString方法返回对象的内容
    }
}


class Student{
    private String name;
    private char sex;
    private int age;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                '}';
    }

    public Student() {
    }

    public Student(String name, char sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}