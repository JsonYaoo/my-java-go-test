package com.jsonyao.cs.aqs;

import java.util.concurrent.TimeUnit;

/**
 * Synchronized修饰的对象的4种方式:
 * a. 对象锁: 锁普通对象, 锁普通方法
 * b. 类锁: 锁静态方法, 锁Class对象
 */
public class MySynchronizedDemo {

    public static void main(String[] args) {
        /**
         * Case 0: 不加锁 => 乱序输出没有规律
         */
//        Thread thread0 = new Thread(new NoneSyncDemo());
//        Thread thread1 = new Thread(new NoneSyncDemo());

        /**
         * Case 1: synchronized锁普通对象 => 对象锁
         */
//        SyncThisObjectDemo syncThisObjectDemo = new SyncThisObjectDemo();
//        Thread thread0 = new Thread(syncThisObjectDemo);// 加锁有效 => thread0输出完thread1输出
//        Thread thread1 = new Thread(syncThisObjectDemo);// 加锁有效 => thread0输出完thread1输出

//        Thread thread0 = new Thread(new SyncThisObjectDemo());// 加锁无效 => 依然乱序输出没有规律
//        Thread thread1 = new Thread(new SyncThisObjectDemo());// 加锁无效 => 依然乱序输出没有规律

        /**
         * Case 2: synchronized锁普通方法 => 对象锁
         */
//        SyncMethodDemo syncMethodDemo = new SyncMethodDemo();
//        Thread thread0 = new Thread(syncMethodDemo);// 加锁有效 => thread0输出完thread1输出
//        Thread thread1 = new Thread(syncMethodDemo);// 加锁有效 => thread0输出完thread1输出

//        Thread thread0 = new Thread(new SyncMethodDemo());// 加锁无效 => 依然乱序输出没有规律
//        Thread thread1 = new Thread(new SyncMethodDemo());// 加锁无效 => 依然乱序输出没有规律

        /**
         * Case 3: synchronized锁静态方法 => 类锁
         */
//        SyncStaticMethodDemo syncStaticMethodDemo = new SyncStaticMethodDemo();
//        Thread thread0 = new Thread(syncStaticMethodDemo);// 加锁有效 => thread0输出完thread1输出
//        Thread thread1 = new Thread(syncStaticMethodDemo);// 加锁有效 => thread0输出完thread1输出

//        Thread thread0 = new Thread(new SyncStaticMethodDemo());// 加锁有效 => thread0输出完thread1输出
//        Thread thread1 = new Thread(new SyncStaticMethodDemo());// 加锁有效 => thread0输出完thread1输出

        /**
         * Case 4: synchronized锁Class对象 => 类锁
         */
//        SyncClassDemo syncClassDemo = new SyncClassDemo();
//        Thread thread0 = new Thread(syncClassDemo);// 加锁有效 => thread0输出完thread1输出
//        Thread thread1 = new Thread(syncClassDemo);// 加锁有效 => thread0输出完thread1输出

        Thread thread0 = new Thread(new SyncClassDemo());// 加锁有效 => thread0输出完thread1输出
        Thread thread1 = new Thread(new SyncClassDemo());// 加锁有效 => thread0输出完thread1输出

        thread0.start();
        thread1.start();
    }

    public static void printLoopInfo(long timeout){
        for(int i = 0; i < 5; i++){
            try {
                System.out.println(Thread.currentThread().getName() + ": " + i);
                TimeUnit.MILLISECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class NoneSyncDemo implements Runnable {

    @Override
    public void run() {
        MySynchronizedDemo.printLoopInfo(200);
    }
}

class SyncThisObjectDemo implements Runnable {

    @Override
    public void run() {
        synchronized (this){
            MySynchronizedDemo.printLoopInfo(200);
        }
    }
}

class SyncMethodDemo implements Runnable {

    @Override
    public synchronized void run() {
        MySynchronizedDemo.printLoopInfo(200);
    }
}

class SyncStaticMethodDemo implements Runnable {

    @Override
    public void run() {
        printLoopInfo();
    }

    public static synchronized void printLoopInfo(){
        MySynchronizedDemo.printLoopInfo(200);
    }
}

class SyncClassDemo implements Runnable {

    @Override
    public void run() {
        synchronized (SyncClassDemo.class){
            MySynchronizedDemo.printLoopInfo(200);
        }
    }
}