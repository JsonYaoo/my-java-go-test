package com.jsonyao.cs.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试可中断锁 & 不可中断锁:
 */
public class MyLockInterruptiblyDemo {

    // 用于不可中断锁
    private static Object objectLock0 = new Object();
    private static Object objectLock1 = new Object();

    // 可中断锁
    private static ReentrantLock reentrantLock0 = new ReentrantLock();
    private static ReentrantLock reentrantLock1 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        /**
         * 不可中断锁
         */
//        Thread thread0 = new Thread(new SynchronizedRunningTask(objectLock0, objectLock1));
//        Thread thread1 = new Thread(new SynchronizedRunningTask(objectLock1, objectLock0));

        /**
         * 可中断锁
         */
        Thread thread0 = new Thread(new ReentrantLockThread(reentrantLock0, reentrantLock1));
        Thread thread1 = new Thread(new ReentrantLockThread(reentrantLock1, reentrantLock0));

        thread0.start();
        thread1.start();

        TimeUnit.MILLISECONDS.sleep(3000);
        System.out.println("主线程开始沉睡3秒完毕!");

        System.out.println("主线程线程" + thread1.getName() + "，在t1上开始执行interrupt()");
        thread1.interrupt();
    }
}

/**
 * Synchronized实现死锁: thread1.interrupt()无法中断死锁现象
 */
class SynchronizedRunningTask implements Runnable {

    private Object lock0;
    private Object lock1;

    public SynchronizedRunningTask(Object lock0, Object lock1) {
        this.lock0 = lock0;
        this.lock1 = lock1;
    }

    @Override
    public void run() {
        try {
            synchronized (lock0){
                // 等待lock0、lock1分别被两个线程获取到, 以产生死锁现象
                TimeUnit.MILLISECONDS.sleep(1000);
                synchronized (lock1){

                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("线程" + Thread.currentThread().getName() + "正常结束!");
        }
    }
}

/**
 * 可中断锁: 可中断获取到锁的线程, 以打破死锁的现象。线程1被中断后, 如果尝试释放对应锁会抛出IllegalMonitorStateException异常
 */
class ReentrantLockThread implements Runnable {

    private ReentrantLock lock0;
    private ReentrantLock lock1;

    public ReentrantLockThread(ReentrantLock lock0, ReentrantLock lock1) {
        this.lock0 = lock0;
        this.lock1 = lock1;
    }

    @Override
    public void run() {
        try {
            lock0.lockInterruptibly();// 获取锁, 可中断
            System.out.println("我是线程" + Thread.currentThread().getName() + " 获取到锁0");
            TimeUnit.MILLISECONDS.sleep(100);// 等待lock0和lock1分别给两个线程获取到, 以产生死锁现象
            lock1.lockInterruptibly();// 获取锁, 可中断
            System.out.println("我是线程" + Thread.currentThread().getName() + " 获取到锁1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            try {
                lock0.unlock();
                lock1.unlock();
            } catch (Exception e) {
                System.out.println("我是线程" + Thread.currentThread().getName() + " 非正常结束! 异常原因: ");
                e.printStackTrace();
                return;
            }
            System.out.println("线程" + Thread.currentThread().getName() + " 正常结束!");// 线程Thread-0正常结束, 线程1状态异常释放锁时抛出异常
        }
    }
}