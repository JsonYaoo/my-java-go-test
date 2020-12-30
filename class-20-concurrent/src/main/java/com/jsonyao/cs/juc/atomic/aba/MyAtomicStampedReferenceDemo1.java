package com.jsonyao.cs.juc.atomic.aba;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 测试AtomicStampedXXX解决ABA问题: 加入版本号, 下一个线程使用上一个版本号会更新失败
 */
public class MyAtomicStampedReferenceDemo1 {

    public static final AtomicStampedReference<String> ATOMIC_STAMPED_REFERENCE = new AtomicStampedReference<String>("abc", 0);

    public static void main(String[] args) {
        for(int i = 0; i < 100; i++){
            final int num = i;
            final int stamp = ATOMIC_STAMPED_REFERENCE.getStamp();
            new Thread(() -> {
                try {
                    TimeUnit.MICROSECONDS.sleep((long) Math.abs((Math.random() * 100)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(ATOMIC_STAMPED_REFERENCE.compareAndSet("abc", "abcd", stamp, stamp+1)){
                    System.out.println("我是线程：" + num + ",我CAS修改为了abcd!");
                }
            }).start();
        }

        // 轮询CAS变更为原来的值
        new Thread(() -> {
            int stamp = ATOMIC_STAMPED_REFERENCE.getStamp();
            while (!ATOMIC_STAMPED_REFERENCE.compareAndSet("abcd", "abc", stamp, stamp+1));
            System.out.println("我是线程：" + Thread.currentThread().getName() + ",我CAS修改回了abc!");
        }).start();
    }

}
