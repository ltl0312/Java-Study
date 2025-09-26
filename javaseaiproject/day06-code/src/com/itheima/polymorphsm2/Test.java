package com.itheima.polymorphsm2;

public class Test {
    public static void main(String[] args) {
        //目标:认识多态的代码
        //1.多态的好处1:右边的对象是解耦合的
        Animal a1 = new Tortoise();
        a1.run();
        //多态不能调用子类特有的方法
        //a1.shrinkHead();


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


    }
}
