package com.itheima.polymorphsm3;

public class Test {
    public static void main(String[] args) {
        //目标:认识多态的代码
        //1.多态的好处1:右边的对象是解耦合的
        Animal a1 = new Tortoise();
        a1.run();
        //多态不能调用子类特有的方法
        //a1.shrinkHead();

        //强制类型转换
        Tortoise t1 = (Tortoise)a1;
        t1.shrinkHead();
        System.out.println("=========================");

        //有继承关系就可以强转,编译阶段不会报错
        //运行时可能会出现类型转换异常:ClassCastException


        Wolf w = new Wolf();
        go(w);
        //w.eatSheep(w);

        Tortoise t = new Tortoise();
        go(t);
    }

    //多态的好处2:父类类型的变量可以作为参数,可以接受一切子类对象
    //宠物游戏:所有的动物都能送给这个游戏跑步
    public static void go(Animal a){
        System.out.println("开始游戏");
        a.run();
        //强制转换前,应该先判断对象的真实类型,再进行强制类型转换
        if (a instanceof Wolf){
            Wolf w1 = (Wolf)a;
            w1.eatSheep();
        }else if (a instanceof Tortoise){
            Tortoise t1 = (Tortoise)a;
            t1.shrinkHead();
        }
        System.out.println("=========================");

    }
}
