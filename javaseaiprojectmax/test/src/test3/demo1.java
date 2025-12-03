package test3;

public class demo1 {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while(true){
                System.out.println("Start new thread!");
            }
        });
        t.start();
        MyThread mt = new MyThread();
        mt.start();
    }

}

class MyThread extends Thread{
    @Override
    public void run() {
        while(true){
            System.out.println("Start new Mythread!");
        }
    }
}