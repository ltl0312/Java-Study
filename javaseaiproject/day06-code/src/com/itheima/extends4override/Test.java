package com.itheima.extends4override;

public class Test {
    public static void main(String[] args) {

    }
}


class Animal{
    public void cry(){
        System.out.println("动物会叫~~~");
    }
}

class Cat extends Animal{

    //方法重写:方法名称和形参列表必须相同,私有方法和静态方法不能重写,重写的方法访问权限必须大于等于父类的方法
    @Override //方法重写的校验注解(标志)
    public  void cry(){
        System.out.println("喵喵喵~~~");
    }
}
