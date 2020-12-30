package com.jsonyao.cs.juc.atomic.aba;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 测试ABA问题: 同样的abc经过abcd ->abc, 对于第二个线程不应该能够设置成功, 但却设置成功了
 */
public class AtomicReferenceAbaDemo1 {

    public static final AtomicReference<String> ATOMIC_REFERENCE = new AtomicReference<String>("abc");

    public static void main(String[] args) {
        for(int i = 0; i < 100; i++){
            final int num = i;
            new Thread(() -> {
                try {
                    TimeUnit.MICROSECONDS.sleep((long) Math.abs((Math.random() * 100)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(ATOMIC_REFERENCE.compareAndSet("abc", "abcd")){
                    System.out.println("我是线程：" + num + ",我CAS修改为了abcd!");
                }
            }).start();
        }

        // 轮询CAS变更为原来的值
        new Thread(() -> {
            while (!ATOMIC_REFERENCE.compareAndSet("abcd", "abc"));
            System.out.println("我是线程：" + Thread.currentThread().getName() + ",我CAS修改回了abc!");
        }).start();
    }

}
