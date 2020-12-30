package juc.tools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 团队出去旅行，大家一起先达到酒店住宿，
 * 然后一起达到游乐的地方游玩，
 * 然后一起坐车回家，
 * 每次需要点名后确认相关人员均达到
 * @author end
 **/
public class CyclicBarrierTest {

    private static final int THREAD_COUNT = 10;

    private final static CyclicBarrier CYCLIC_BARRIER = new CyclicBarrier(THREAD_COUNT  ,
            new Runnable() {
                public void run() {
                    System.out.println("======>我是导游，本次点名结束，准备走下一个环节!");
                }
            }
    );

    public static void main(String []args)
            throws InterruptedException, BrokenBarrierException {
        for(int i = 0 ; i < THREAD_COUNT ; i++) {
            new Thread(String.valueOf(i)) {
                public void run() {
                    try {
                        System.out.println("我是线程：" + this.getName() + " 我们达到旅游地点！");
                        CYCLIC_BARRIER.await();
                        System.out.println("我是线程：" + this.getName() + " 我开始骑车！");
                        CYCLIC_BARRIER.await();
                        System.out.println("我是线程：" + this.getName() + " 我们开始爬山！");
                        CYCLIC_BARRIER.await();
                        System.out.println("我是线程：" + this.getName() + " 我们回宾馆休息！");
                        CYCLIC_BARRIER.await();
                        System.out.println("我是线程：" + this.getName() + " 我们开始乘车回家！");
                        CYCLIC_BARRIER.await();
                        System.out.println("我是线程：" + this.getName() + " 我们到家了！");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
