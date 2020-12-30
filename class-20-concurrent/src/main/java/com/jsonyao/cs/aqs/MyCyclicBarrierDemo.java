package com.jsonyao.cs.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 测试CyclicBarrier: 子任务循环执行不同内容, 触发主任务不同的Action
 */
public class MyCyclicBarrierDemo {

    // CyclicBarrier计数个数
    private static final int NUMS = 5;

    // CyclicBarrier
    public static final CyclicBarrier myCyClicBarrier = new CyclicBarrier(NUMS, new MasterRunningTask());

    public static void main(String[] args) {
        for(int i = 0; i < NUMS; i++){
            Thread thread = new Thread(new StudentRunningTask(i, myCyClicBarrier));
            thread.start();
        }
    }

}

/**
 * 主任务: barrierAction：每当计数器一次计数完成后——CyclicBarrier.await()时，系统会执行的动作。
 */
class MasterRunningTask implements Runnable {

    // 主任务步数
    private static int step = 0;

    @Override
    public void run() {
        if(step == 0){
            System.out.println("同学们都已经上大巴了，咱们出发!");
        }else if(step == 1){
            System.out.println("所有大巴都到了，同学们开始春游!");
        }else if(step == 2){
            System.out.println("春游结束啦!");
        }

        step++;
    }

}

/**
 * 子任务
 */
class StudentRunningTask implements Runnable {

    // 子任务序号
    private int studenNo = 0;

    // 当前绑定的CyclicBarrier
    private CyclicBarrier cyclicBarrier;

    public StudentRunningTask(int studenNo, CyclicBarrier cyclicBarrier) {
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

            System.out.println("学生" + studenNo + ", 已经回到巴士。");
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
