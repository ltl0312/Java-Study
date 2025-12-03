package test;

//class MyThread extends Thread {
//    @Override
//    public void run() {
//        System.out.println("这⾥是线程运⾏的代码");
//    }
//}
public class Test {
    public static void main(String[] args)  {
//        MyThread t = new MyThread();
//        t.start();
        Thread t = new Thread() {
            @Override
            public void run() {
                System.out.println("这⾥是线程运⾏的代码");
            }
        };
        //Thread不是函数式接口，所以不能用Lambda表达式
        Thread t1 = new Thread(() -> {
                System.out.println("这⾥是线程运⾏的代码");
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("这⾥是线程运⾏的代码");
            }
        });
        Runnable t3 = () -> System.out.println("这⾥是线程运⾏的代码");



    }
}
