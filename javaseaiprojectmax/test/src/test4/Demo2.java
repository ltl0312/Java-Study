package test4;

import java.util.HashSet;

public class Demo2 {
    public static void main(String[] args) {
        HashSet<Student> hs = new HashSet<>();
        hs.add(new Student(18,"zhangsan"));
        hs.add(new Student(20,"lisa"));
        hs.add(new Student(20,"lisa"));
        for (Student s : hs){
            System.out.println(s);
        }
        System.out.println(hs);
    }
}

class Student{
    public String name;
    public int age;
    public Student(int age,String name){
        this.name=name;
        this.age=age;
    }
    public String toString(){
        return "name:"+name+",age:"+age;
    }
    public int hashCode(){
        return this.name.hashCode() + this.age;
    }
    public boolean equals(Object obj){
        Student s = (Student)obj;
        return this.name.equals(s.name) && this.age == s.age;
    }
}