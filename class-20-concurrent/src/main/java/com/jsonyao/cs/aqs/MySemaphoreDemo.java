package com.jsonyao.cs.aqs;

import java.util.concurrent.Semaphore;

/**
 * 测试Semaphore: 通过acquire()、release()信号量，分批次循环中的子任务
 */
public class MySemaphoreDemo implements Runnable{

    Semaphore mySemaphore = new Semaphore(5);// 0时会do nothing

    public static void main(String[] args) {
        MySemaphoreDemo mySemaphoreDemo = new MySemaphoreDemo();
        for(int i = 0; i < 10; i++){
//            Thread thread = new Thread(new MySemaphoreDemo());// 会一口气哗啦的全部执行完毕
            Thread thread = new Thread(mySemaphoreDemo);
            thread.start();
        }
    }

    @Override
    public void run() {
        try {
            mySemaphore.acquire();

            Thread.sleep(1000);
            System.out.println(System.currentTimeMillis()/1000 + "," + Thread.currentThread().getName() + ", 执行完毕!");

            mySemaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
