package com.itheima.innerclass2;
//外部类
public class Outer {
    public static String schoolName = "黑马程序员";
    //静态内部类:属于外部类本身持有
    public static class Inner {
        private String name;

        public void show() {
            System.out.println(schoolName);
        }

        public Inner(String name) {
            this.name = name;
        }

        public Inner() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
