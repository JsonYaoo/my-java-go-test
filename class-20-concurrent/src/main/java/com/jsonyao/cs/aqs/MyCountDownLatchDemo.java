package com.jsonyao.cs.aqs;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 测试CountDownLatch
 */
public class MyCountDownLatchDemo {

    // CountDownLatch计数个数
    private static final Integer NUMS = 5;

    // CountDownLatch
    private volatile static CountDownLatch myCountDownLatch = new CountDownLatch(NUMS);

    public static void main(String[] args) throws InterruptedException {
        // 构造线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 一共生产15个线程
        int i = 0;
        while (i < 15){
            executorService.submit(new CountDownLatchRunningTask(i++, myCountDownLatch));
        }

        // 主线程等待NUMS个子任务执行完毕后, 继续往下执行
        myCountDownLatch.await();
        System.out.println(NUMS + "个子任务全部执行完毕!");

        // 关闭线程池
        executorService.shutdown();
    }

}

class CountDownLatchRunningTask implements Runnable{

    // 任务序号
    private int i;

    // 绑定的CountDownLatch
    private CountDownLatch countDownLatch;

    public CountDownLatchRunningTask(int i, CountDownLatch countDownLatch) {
        this.i = i;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        // 睡眠随机时间
        try {
            Random random = new Random();
            int sleepTime = random.nextInt(1000);
            TimeUnit.MILLISECONDS.sleep(sleepTime);

            // 子任务发生异常，也被算作子任务执行完毕(直接执行Finally, 不会抛出异常)。不会影响其他线程和CountDownLatch
            if(i == 3){
               throw new RuntimeException();
            }

            System.out.println(Thread.currentThread().getName() + ": 子任务执行完毕! sleepTime=" + sleepTime);
        } catch (InterruptedException e) {// 不会抛出异常 -> Exception：则会抛出异常
            e.printStackTrace();
        } finally {
            // 线程执行完毕后调用 -> 签个到
            this.countDownLatch.countDown();
        }
    }
}