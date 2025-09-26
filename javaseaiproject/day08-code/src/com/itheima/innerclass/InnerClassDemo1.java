package com.itheima.innerclass;

public class InnerClassDemo1 {
    public static void main(String[] args) {
        //目标:搞清楚成员内部类的语法
        //成员内部类创建对象的格式
        //外部类名.内部类名 对象名 = new 外部类名.内部类名();
        Outer.Inner oi = new Outer().new Inner();
        oi.show();
        oi.setName("张三");
        System.out.println(oi.getName());
        //成员内部类访问外部类成员的特点(拓展)
        //成员内部类中可以直接访问外部类的静态成员,也可以直接访问外部类的实例成员
        System.out.println(Outer.schoolName);
        //成员内部类的实例方法中,可以直接拿到当前寄生的外部类对象:外部类名.this

        People.Heart heart = new People().new Heart();
        heart.show();

    }

}

class People{
    private int heartBeat = 100;
    public class Heart{
        private int heartBeat = 80;
        public void show(){
            int heartBeat = 200;
            System.out.println(heartBeat);//200
            System.out.println(this.heartBeat);//80
            System.out.println(People.this.heartBeat);//100
        }
    }
}










