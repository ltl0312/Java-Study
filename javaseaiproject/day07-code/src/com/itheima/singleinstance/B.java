package com.itheima.singleinstance;

//懒汉式单例类
public class B {
    //1.私有化构造器
    private B(){

    }
    //2.创建一个静态的成员变量，保存B类的唯一实例
    private static B instance;
    //3.创建一个静态的成员方法:真正需要的实例的时候，调用这个方法，返回唯一的实例
    public static B getInstance(){
        if(instance == null){
            instance = new B();
        }
        return instance;
    }
}
