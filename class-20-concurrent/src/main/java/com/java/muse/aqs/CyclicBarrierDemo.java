package com.java.muse.aqs;

import java.util.concurrent.CyclicBarrier;

/**
 * 测试CyclicBarrier
 *
 * 同学们去春游
 * 首先：同学们都先上公司门口的大巴。人齐了之后，巴士出发。
 * 其次：所有巴士都到达景点后，大家集合，开始春游。
 *
 * @author muse
 */
public class CyclicBarrierDemo {

    private static final int NUMS = 5;

    public static final CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMS, new Master());

    public static void main(String[] args) {
        for (int i = 0; i < NUMS; i++) {
            Thread thread = new Thread(new Student(i, cyclicBarrier));
            thread.start();
        }
    }

}

class Student implements Runnable {

    private CyclicBarrier cyclicBarrier;

    private volatile Integer studenNo = 0;

    public Student(Integer studenNo, CyclicBarrier cyclicBarrier) {
        this.studenNo = studenNo;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            System.out.println("学生" + studenNo + ", 已经上巴士。");
            cyclicBarrier.await();

            System.out.println("学生" + studenNo + ", 巴士已经到达目的地。");
            cyclicBarrier.await();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}

/**
 * barrierAction：每当计数器一次计数完成后——CyclicBarrier.await()时，系统会执行的动作。
 */
class Master implements Runnable {

    private static int step = 1;

    @Override
    public void run() {
        if (step == 1) {
            System.out.println("同学们都已经上大巴了，咱们出发！");
        } else if (step == 2) {
            System.out.println("所有大巴都到了，同学们开始春游！");
        }
        step++;
    }
}