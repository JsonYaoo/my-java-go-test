package com.jsonyao.cs.juc.collections;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 测试同步队列
 */
public class MySynchronousQueue {

    /**
     * 测试结果: 四种操作队列的方法还是维持队列方法的特性一样
     * add(E)与remove(): 还是会抛出异常, 一对且非并发使用时, 会永远抛出异常
     * offer(E)与poll(): 还是不会抛出异常, 一对且非并发使用时, 会永远返回false
     * put(E)与take(): 还是会阻塞等待直到成功, 一对且非并发使用时, take可以取到值, 而put没有返回值
     * offer(E, long, TimeUnit)与poll(long, TimeUnit): 还是会等待指定时间后返回, 一对且非并发使用时, poll可以取到值, offer也可以为true
     */
    public static void main(String[] args) {
        final SynchronousQueue<Integer> queue = new SynchronousQueue<>();

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
//                    System.err.println(Thread.currentThread().getName() + ": " + queue.add(12));// Exception in thread "producer" java.lang.IllegalStateException: Queue full

                    System.err.println(Thread.currentThread().getName() + ": " + queue.offer(12));// producer: false

//                    try {
//                        queue.put(12);// 已添加
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.err.println(Thread.currentThread().getName() + ": " + "已添加");

//                    try {
//                        queue.offer(12, 10, TimeUnit.MILLISECONDS);// 已延迟添加
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.err.println(Thread.currentThread().getName() + ": " + "已延迟添加");

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        producer.setName("producer");

        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    System.err.println(Thread.currentThread().getName() + ": " + queue.remove());// Exception in thread "consumer" java.util.NoSuchElementException

//                    System.err.println(Thread.currentThread().getName() + ": " + queue.poll());// consumer: null

//                    try {
//                        System.err.println(Thread.currentThread().getName() + ": " + queue.take());// consumer: 12
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }

//                    try {
//                        System.err.println(Thread.currentThread().getName() + ": " + queue.poll(10, TimeUnit.MILLISECONDS));// consumer: 12
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        });
        consumer.setName("consumer");

        producer.start();
        consumer.start();

        while (true);
    }
}
