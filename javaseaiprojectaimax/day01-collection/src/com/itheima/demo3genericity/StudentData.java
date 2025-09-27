package com.itheima.demo3genericity;

public class StudentData implements Data<Student>{
    @Override
    public void add(Student student) {

    }

    @Override
    public void delete(Student student) {

    }

    @Override
    public void update(Student student) {

    }

    @Override
    public Student query(int id) {
        return new Student();
    }
}
