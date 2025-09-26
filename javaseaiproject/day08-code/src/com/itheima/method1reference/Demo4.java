package com.itheima.method1reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Demo4 {
    public static void main(String[] args) {
        //目标:理解构造器引用
        //创造了接口的匿名内部类对象
//        CarFactory cf = new CarFactory() {
//            @Override
//            public Car getCar(String name) {
//                return new Car(name);
//            }
//        };
        //CarFactory cf = name -> new Car(name);

        //构造器引用: 类名::new
        //如果某个 Lambda 表达式里只是在创建对象,并且 "→" 前后参数情况一致,就可以使用构造器引用
        CarFactory cf = Car::new;
        Car c = cf.getCar("保时捷");
        System.out.println(c);
    }
}


interface CarFactory{
    Car getCar(String name);
}

@Data
@NoArgsConstructor
@AllArgsConstructor

class Car{
    private String name;
}