package javaprogramming.demo28Comparatorsort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        //目标：定义一个Person类，包含name和age属性，使用Comparator对Person对象按年龄进行升序排序。
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("张三", 18));
        persons.add(new Person("李四", 17));
        persons.add(new Person("王五", 19));
        persons.add(new Person("赵六", 16));
        persons.add(new Person("孙七", 20));
        System.out.println("排序前：");
        for (Person person : persons) {
            System.out.println(person.getName() + " " + person.getAge());
        }
        System.out.println("排序后：");
        // 使用匿名内部类
        Collections.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person s1, Person s2) {
                return s1.getAge() - s2.getAge();
            }
        });
        for (Person person : persons) {
            System.out.println(person.getName() + " " + person.getAge());
        }

    }
}
