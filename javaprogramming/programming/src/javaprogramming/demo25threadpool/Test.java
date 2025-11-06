package javaprogramming.demo25threadpool;

import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) {
        //目标：使用ExecutorService创建一个线程池，并提交多个任务。
        //七个参数：1.核心线程数 2.最大线程数 3.线程空闲时间 4.时间单位 5.任务队列 6.线程工厂 7.拒绝策略
        ExecutorService pool = new ThreadPoolExecutor(3, 5,
                10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

        Runnable target = new MyRunnable();
        pool.execute(target);
        pool.execute(target);
        pool.execute(target);
        pool.execute(target);
        pool.execute(target);
        pool.execute(target);
        pool.execute(target);
        pool.execute(target);
        pool.execute(target);

        pool.shutdown();
    }
}
