package com.jsonyao.cs.juc.atomic.aba;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * 测试AtomicMarkableReference解决ABA问题: 对于需要多版本的场景不容易解决
 */
public class MyAtomicMarkableReferenceDemo3 {

    // 会有ABA问题
    public static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(100);

    // 不会有ABA问题
    public static final AtomicMarkableReference<Integer> ATOMIC_MARKABLE_REFERENCE = new AtomicMarkableReference<>(100, false);

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
            ATOMIC_MARKABLE_REFERENCE.compareAndSet(100, 101, false, true);

            ATOMIC_MARKABLE_REFERENCE.compareAndSet(101, 100, true, false);
        });

        Thread threadRef1 = new Thread(() -> {
            boolean isMark = ATOMIC_MARKABLE_REFERENCE.isMarked();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 尝试更新 => 更新失败
            boolean result = ATOMIC_MARKABLE_REFERENCE.compareAndSet(100, 101, isMark, !isMark);
            System.out.println(result);// false
        });

        threadRef0.start();
        threadRef1.start();
    }
}
