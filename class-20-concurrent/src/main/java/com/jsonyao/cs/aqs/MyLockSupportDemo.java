package com.jsonyao.cs.aqs;

import java.util.concurrent.locks.LockSupport;

/**
 * 测试LockSupport: 在run()方法里执行park(), 如果没有unpark通行证会阻塞当前线程的运行, 直到获取到unpark()通行证, 如果有则直接通过 => unpark的线程必须已经是启动的, 否则没有效果
 */
public class MyLockSupportDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread thread0 = new Thread(new MyRunningTask0());
        Thread thread1 = new Thread(new MyRunningTask1());

        thread0.start();

        Thread.sleep(2000);
        System.out.println("主线程sleep 2秒完毕");

        thread1.start();

        LockSupport.unpark(thread0);// unpark的线程必须已经是启动的, 否则没有效果
        System.out.println("LockSupport.unpark(thread0) 执行完毕 ");

        LockSupport.unpark(thread1);// unpark的线程必须已经是启动的, 否则没有效果
        System.out.println("LockSupport.unpark(thread1) 执行完毕 ");
    }

}

class MyRunningTask0 implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始执行!");
        LockSupport.park();// 在run()方法里执行park, 会阻塞当前线程的运行, 直到获取到unpark()通行证
        System.out.println(Thread.currentThread().getName() + "执行完毕!");
    }

}

class MyRunningTask1 implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始执行!");

        // 线程2的任务先睡3s, 在睡的途中给予unpark()通行证 => 睡醒后拿着unpark通行证, 直接通过
        try {
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + "sleep 3s完毕!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 在run()方法里执行park(), 如果没有unpark通行证会阻塞当前线程的运行, 直到获取到unpark()通行证, 如果有则直接通过
        LockSupport.park();
        System.out.println(Thread.currentThread().getName() + "执行完毕!");
    }

}