package javaprogramming.demo17ProducerConsumerModel;

public class Test {
    public static void main(String[] args) throws Exception {
        //目标：使用多线程和wait()/notify()机制，实现生产者-消费者模型。
        Resource resource = new Resource();
        Producer p = new Producer(resource);
        p.setName("生产者");
        Consumer c = new Consumer(resource);
        c.setName("消费者");
        p.start();
        c.start();

    }
}
