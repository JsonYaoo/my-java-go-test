package com.jsonyao.cs.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试Condition: 实现ReentrantLock的线程交互
 */
public class MyConditionDemo {

    // 可重用锁
    public static ReentrantLock lock = new ReentrantLock();

    // 阻塞/唤醒
    public static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                lock.lock();// 1、获取当前锁
                System.out.println(System.currentTimeMillis() / 1000 + " " + Thread.currentThread().getName() + ", " + "子线程开始等待！");

                condition.await();// 2、释放当前锁, 并进入等待状态, 直到获取到锁的线程调用single(): 注意, 调用await()前必须获取到当前锁
                System.out.println(System.currentTimeMillis() / 1000 + " " + Thread.currentThread().getName() + ", " + "子线程继续执行！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();// 5、释放当前锁
                System.out.println(System.currentTimeMillis() / 1000 + " " + Thread.currentThread().getName() + ", " + "子线程调用了unlock！");
            }
        }).start();

        Thread.sleep(1000);
        System.out.println(System.currentTimeMillis() / 1000 + " " + Thread.currentThread().getName() + ", 主线程睡了第1秒钟！");
        Thread.sleep(1000);
        System.out.println(System.currentTimeMillis() / 1000 + " " + Thread.currentThread().getName() + ", 主线程睡了第2秒钟！");

        lock.lock();// 3、获取当前锁

        condition.signal();// 4、释放当前锁, 唤醒await()的线程, 并进入阻塞状态等到await()的线程释放锁: 注意, 调用singal()之前必须获取到当前锁
        System.out.println(System.currentTimeMillis() / 1000 + " " + Thread.currentThread().getName() + ", 主线程调用了signal！");

        lock.unlock();// 6、释放当前锁
        System.out.println(System.currentTimeMillis() / 1000 + " " + Thread.currentThread().getName() + ", 主线程调用了unlock！");
    }

}
