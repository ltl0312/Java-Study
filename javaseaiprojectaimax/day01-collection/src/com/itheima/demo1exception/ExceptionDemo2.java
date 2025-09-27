package com.itheima.demo1exception;

public class ExceptionDemo2 {
    public static void main(String[] args) {
        // 目标：搞清楚异常的作用。
        System.out.println("程序开始执行...");
        try {
            System.out.println(div(10, 0));
            System.out.println("底层方法执行成功了~~~");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("底层方法执行失败了~~");
        }
        System.out.println("程序结束执行...");
    }

    // 需求：求2个数的除的结果，并返回这个结果。
    public static int div(int a, int b) throws Exception {
        if(b == 0){
            System.out.println("除数不能为0，您的参数有问题！");
            // 可以返回一个异常给上层调用者，返回的异常还能告知上层底层是执行成功了还是执行失败了！！
            throw new Exception("除数不能为0，您的参数有问题！");
        }
        int result = a / b;
        return result;
    }
}
