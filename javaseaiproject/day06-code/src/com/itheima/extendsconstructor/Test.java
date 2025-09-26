package com.itheima.extendsconstructor;



public class Test {
    public static void main(String[] args) {
        //认识子类构造器的特点,再看应用场景
        //子类构造器必须先调用父类构造器,再执行子类构造器代码
        Zi zi = new Zi();
    }
}

class Zi extends Fu {
    public Zi()
    {
        super();//默认存在的,写不写都有
        System.out.println("子类无参构造器");
    }
    public Zi(int age)
    {
        super(age);
        System.out.println("子类有参构造器");
    }
}

class Fu {
    public Fu()
    {
        System.out.println("父类无参构造器");
    }
    public Fu(int age)
    {
        System.out.println("父类有参构造器");
    }
}