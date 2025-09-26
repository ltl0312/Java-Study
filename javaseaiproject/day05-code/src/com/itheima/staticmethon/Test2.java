package com.itheima.staticmethon;

public class Test2 {
    public static void main(String[] args) {
        //目标:搞清楚静态方法的应用:可以做工具类
        //登录
        //开发一个验证码程序
        String code = generateVerifyCode(4);
        System.out.println(code);
    }
    public static String generateVerifyCode(int length){
        String code = "";
        for (int i = 0; i < length; i++) {
            int number = (int)(Math.random()*10);
            code += number;
        }
        return code;
    }
}
