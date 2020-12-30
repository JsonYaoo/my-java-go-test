package com.jsonyao.cs.juc.atomic.aba;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 测试ABA问题(更好理解的案例):
 * Reference: https://www.cnblogs.com/549294286/p/3766717.html
 */
public class MyAtomicIntegerAbaDemo2 {

    // 会有ABA问题
    public static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(100);

    // 不会有ABA问题
    public static final AtomicStampedReference<Integer> ATOMIC_STAMPED_REFERENCE = new AtomicStampedReference<Integer>(100, 0);

    public static void main(String[] args) throws InterruptedException {
        Thread thread0 = new Thread(() -> {
            ATOMIC_INTEGER.compareAndSet(100, 101);
            ATOMIC_INTEGER.compareAndSet(101, 100);
        });

        Thread thread1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 尝试更新 => 通过更新
            boolean result = ATOMIC_INTEGER.compareAndSet(100, 101);
            System.out.println(result);// true
        });

        thread0.start();
        thread1.start();
        thread0.join();
        thread1.join();

        Thread threadRef0 = new Thread(() -> {
            ATOMIC_STAMPED_REFERENCE.compareAndSet(100, 101,
                    ATOMIC_STAMPED_REFERENCE.getStamp(), ATOMIC_STAMPED_REFERENCE.getStamp()+1);

            ATOMIC_STAMPED_REFERENCE.compareAndSet(101, 100,
                    ATOMIC_STAMPED_REFERENCE.getStamp(), ATOMIC_STAMPED_REFERENCE.getStamp()+1);
        });

        Thread threadRef1 = new Thread(() -> {
            int stamp = ATOMIC_STAMPED_REFERENCE.getStamp();

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 尝试更新 => 更新失败
            boolean result = ATOMIC_STAMPED_REFERENCE.compareAndSet(100, 101, stamp, stamp + 1);
            System.out.println(result);// false
        });

        threadRef0.start();
        threadRef1.start();
    }
}
