package com.jsonyao.cs.note;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 并发工具题目测试: ABCD四个线程, [1,2,3,4,5,6,7,8,9,10] => 按顺序输出A1,B1,C1,D1,A2,B2,C2,D2...
 */
public class LockSupportDemo {

    private final static List<Integer> PRINT_LIST = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    public static void main(String[] args) throws Throwable {
        Thread threadA = new Thread(new RunningDemo(PRINT_LIST));
        threadA.setName("A");
        Thread threadB = new Thread(new RunningDemo(PRINT_LIST));
        threadB.setName("B");
        Thread threadC = new Thread(new RunningDemo(PRINT_LIST));
        threadC.setName("C");
        Thread threadD = new Thread(new RunningDemo(PRINT_LIST));
        threadD.setName("D");

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        for (int i = 0; i < PRINT_LIST.size(); i++) {
            LockSupport.unpark(threadA);
            TimeUnit.MILLISECONDS.sleep(30);

            LockSupport.unpark(threadB);
            TimeUnit.MILLISECONDS.sleep(30);

            LockSupport.unpark(threadC);
            TimeUnit.MILLISECONDS.sleep(30);

            LockSupport.unpark(threadD);
            TimeUnit.MILLISECONDS.sleep(30);
        }

    }
}

class RunningDemo implements Runnable {

    private List<Integer> printList;

    public RunningDemo(List<Integer> printList) {
        this.printList = printList;
    }

    @Override
    public void run() {
        for (int i = 0; i < printList.size(); i++) {
            LockSupport.park();
            System.err.println(Thread.currentThread().getName() + ": " + printList.get(i));
        }
    }
}