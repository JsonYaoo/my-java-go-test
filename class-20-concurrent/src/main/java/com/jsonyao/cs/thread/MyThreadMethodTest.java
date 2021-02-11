package com.jsonyao.cs.thread;

/**
 * 线程方法测试类
 * Relation:
 *      a. https://blog.csdn.net/qq_22771739/article/details/82529874
 *      b. https://blog.csdn.net/wangshuang1631/article/details/53815519
 *      c. https://blog.csdn.net/xiangwanpeng/article/details/54972952
 */
public class MyThreadMethodTest {

    public static void main(String[] args) throws Exception{
        MyThreadMethodTest test = new MyThreadMethodTest();

        /**
         * 1. 测试线程睡眠时, 是否会释放synchronized锁
         */
//        test.testSleep();

        /**
         * 2. 测试Object#wait()、Object#notify()、Object#notifyAll()是否必须加synchronized才能执行
         * => 可见, 会抛出java.lang.IllegalMonitorStateException: 表示必须锁上对象后, 才能调用被锁对象的这个三个方法
         */
        // 会抛出java.lang.IllegalMonitorStateException
//        test.wait();
//        test.notify();
//        test.notifyAll();
//        Thread.sleep(10000);
//
//        // 虽然不会抛出java.lang.IllegalMonitorStateException, 但是由于test#wait()使得main线程进入等待状态, 而没有唤醒的机会
//        synchronized (test) {// 且必须是对象, synchronized (MyThreadMethodTest.class)将仍然抛出IllegalMonitorStateException异常
//            test.wait(1);// 可以设置超时时间, 过期自动唤醒
//            System.err.println("test#wait()执行完毕!");
//        }
//        synchronized (test) {
//            test.notify();
//            System.err.println("test#notify()执行完毕!");
////            test.notifyAll();
////            System.err.println("test#notifyAll()执行完毕!");
//        }

        /**
         * 2. 测试wait()后, 能否释放synchronized锁
         */
//        test.testWaitNotify();

        /**
         * 3. 测试任务本身能不能作为对象监视器
         */
        test.testSelfWaitNotify();
    }

    /**
     * 1. 测试线程睡眠时, 是否会释放synchronized锁
     * => 可见, Thread.sleep()并不会释放synchronized锁, 等待时仍然持有锁
     */
    public void testSleep() throws Exception {
        Thread thread0 = new Thread(new SleepDemo());
        Thread thread1 = new Thread(new SleepDemo());

        thread0.start();
        thread1.start();
    }

    /**
     * 2. 测试wait()后, 能否释放synchronized锁
     * => 可见: wait()后会释放synchronized锁, 但sleep()会继续持有synchronized锁, 而且notify()确实是随机唤醒一个在等待队列的线程, 且再次与同步队列争抢synchronized锁
     */
    public void testWaitNotify() throws Exception {
        Thread waitT0 = new Thread(new WaitDemo(this));
        Thread waitT1 = new Thread(new WaitDemo(this));
        Thread notifyT0 = new Thread(new NotifyDemo(this));
        Thread notifyT1 = new Thread(new NotifyDemo(this));

        waitT0.start();
        waitT1.start();

        System.err.println(Thread.currentThread().getName() + "即将睡眠10s...");
        Thread.sleep(10000);
        System.err.println(String.format("睡眠结束, 即将唤醒使用监视器%s的线程...", this.getClass().getName()));

        notifyT0.start();
        notifyT1.start();
    }

    /**
     * 3. 测试任务本身能不能作为对象监视器
     * => 可见是可以的, Wait2NotifyDemo里面指的是wait2NotifyDemo对象, 而且在主线程调用notify()时还需要使用synchronized对wait2NotifyDemo进行加锁
     */
    public void testSelfWaitNotify() throws Exception {
        Wait2NotifyDemo wait2NotifyDemo = new Wait2NotifyDemo();
        Thread thread = new Thread(wait2NotifyDemo);
        thread.start();

        System.err.println(Thread.currentThread().getName() + "即将睡眠10s...");
        Thread.sleep(10000);
        System.err.println(String.format("睡眠结束, 即将唤醒使用监视器%s的线程...", wait2NotifyDemo.getClass().getName()));

        synchronized (wait2NotifyDemo) {
            wait2NotifyDemo.notify();
        }
    }
}

/**
 * Thread.sleep()测试demo
 */
class SleepDemo implements Runnable{

    @Override
    public void run() {
        synchronized (MyThreadMethodTest.class) {
            System.err.println(Thread.currentThread().getName() + "执行SleepDemo#run(), 即将睡眠10s...");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.err.println(Thread.currentThread().getName() + "睡眠结束...");
            }
        }
    }
}

/**
 * Object#wait()测试Demo
 */
class WaitDemo implements Runnable {

    /**
     * 对象监视器
     */
    private MyThreadMethodTest objectMonitor;

    public WaitDemo(MyThreadMethodTest objectMonitor) {
        this.objectMonitor = objectMonitor;
    }

    @Override
    public void run() {
        synchronized (objectMonitor) {
            try {
                System.err.println(String.format("线程%s执行WaitDemo#run(), 即将进入等待状态, 对象监视器为%s...",
                        Thread.currentThread().getName(), objectMonitor.getClass().getName()));
                objectMonitor.wait();
                System.err.println(String.format("线程%s执行WaitDemo#run(), 被唤醒, 对象监视器为%s, 即将睡眠10s...",
                        Thread.currentThread().getName(), objectMonitor.getClass().getName()));
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * Object#notify()测试Demo
 */
class NotifyDemo implements Runnable {

    /**
     * 对象监视器
     */
    private MyThreadMethodTest objectMonitor;

    public NotifyDemo(MyThreadMethodTest objectMonitor) {
        this.objectMonitor = objectMonitor;
    }

    @Override
    public void run() {
        synchronized (objectMonitor) {
            try {
                System.err.println(String.format("线程%s执行NotifyDemo#run(), 即将唤醒对象监视器为%s的线程...",
                        Thread.currentThread().getName(), objectMonitor.getClass().getName()));
                objectMonitor.notify();
                System.err.println(String.format("线程%s执行NotifyDemo#run(), 已唤醒对象监视器为%s的线程, 即将睡眠10s...",
                        Thread.currentThread().getName(), objectMonitor.getClass().getName()));
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * Object#wait() & notify()测试Demo
 */
class Wait2NotifyDemo implements Runnable {

    @Override
    public void run() {
        synchronized (this) {
            try {
                System.err.println(String.format("线程%s执行SleepDemo#run(), 即将进入等待状态, 对象监视器为%s...",
                        Thread.currentThread().getName(), this.getClass().getName()));
                wait();
                System.err.println(String.format("线程%s执行SleepDemo#run(), 被唤醒, 对象监视器为%s, 即将睡眠10s...",
                        Thread.currentThread().getName(), this.getClass().getName()));
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}