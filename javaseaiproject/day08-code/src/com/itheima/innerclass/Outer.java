package com.itheima.innerclass;

//外部类
public class Outer {
    public static String schoolName = "黑马程序员";
    //成员内部类,无static修饰,属于外部类对象持有
    public String companyName = "传智播客";
    public void run(){

    }
    public class Inner{
        private String name;
        public void show(){
            System.out.println("show...");
            System.out.println(schoolName);
            System.out.println(companyName);
            run();
            System.out.println(this);//自己的对象
            System.out.println(Outer.this);//寄生的外部类对象

        }

        public Inner() {
        }

        public Inner(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        @Override
        public String toString() {
            return super.toString();
        }
    }


}
