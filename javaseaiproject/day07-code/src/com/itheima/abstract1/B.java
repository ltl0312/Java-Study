package com.itheima.abstract1;

//一个类继承抽象类,必须重写抽象类中的抽象方法,否则该类也必须为抽象类
public class B extends A{
    @Override
    public void show()
    {
        System.out.println("B");
    }
}
