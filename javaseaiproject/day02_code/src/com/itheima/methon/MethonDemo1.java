package com.itheima.methon;

public class MethonDemo1 {
    public static void main(String[] args) {
        //目标:掌握方法的定义和调用
        int sum = getSum(10,20);
        System.out.println("和是:" + sum);
        System.out.println("-----------------------");

        int sum2 = getSum(100,200);
        System.out.println("和是:" + sum2);
        System.out.println("-----------------------");

        PrintHelloWorld();

        String code = getVerifyCode(4);
        System.out.println(code);

    }

    //定义一个方法,求任意两个整数的和并返回
    public static int getSum(int a,int b){
        int sum = a + b;
        return sum;
    }

    //定义一个方法,打印3行"hello world"
    public static void PrintHelloWorld(){
        System.out.println("hello world");
        System.out.println("hello world");
        System.out.println("hello world");

    }

    //定义一个方法,获取指定位数的验证码返回
    public static String getVerifyCode(int n){
        String code = "";
        for (int i = 0; i < n; i++) {
            int number = (int)(Math.random() * 10);
                    code += number;
                    //System.out.println(code);

        }
        return code;
    }
}
