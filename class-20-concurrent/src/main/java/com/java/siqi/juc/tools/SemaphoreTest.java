package com.java.siqi.juc.tools;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * 这里是简单模拟并发100个线程去访问一段程序，此时要控制最多同时运行的是10个，
 * 用到了这个信号量，运行程序用了一个线程睡眠一个随机的时间来代替，
 * 后面有线程说自己释放了，就有线程获得了，没释放是获取不到的
 * @author end
 **/
public class SemaphoreTest {

    private final static Semaphore MAX_SEMA_PHORE = new Semaphore(10);
    public static void main(String []args) {
        for(int i = 0 ; i < 100 ; i++) {
            final int num = i;
            final Random radom = new Random();
            new Thread() {
                public void run() {
                    boolean acquired = false;
                    try {
                        MAX_SEMA_PHORE.acquire();
                        acquired = true;
                        System.out.println("我是线程：" + num + " 我获得了使用权！" + System.currentTimeMillis());
                        long time = 1000 * Math.max(1, Math.abs(radom.nextInt() % 10));
                        Thread.sleep(time);
                        System.out.println("我是线程：" + num + " 我执行完了！" + System.currentTimeMillis());
                    }catch(Exception e) {
                        e.printStackTrace();
                    }finally {
                        if(acquired) {
                            MAX_SEMA_PHORE.release();
                        }
                    }
                }
            }.start();
        }
    }

}
