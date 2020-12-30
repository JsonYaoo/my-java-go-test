package com.jsonyao.cs.juc.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 测试AtomicReference
 * 相关方法列表
 * @see AtomicReference#compareAndSet(Object, Object) 对比设置值，参数1：原始值，参数2：修改目标引用
 * @see AtomicReference#getAndSet(Object) 将引用的目标修改为设置的参数，直到修改成功为止，返回修改前的引用
 */
public class MyAtomicReferenceDemo {

    public static final AtomicReference<String> ATOMIC_REFERENCE = new AtomicReference<String>("a");

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++){
            new Thread(() -> {
                try {
                    Thread.sleep(Math.abs((int )Math.random() * 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /**
                 * 1、ATOMIC_REFERENCE.getAndSet
                 */
                if(ATOMIC_REFERENCE.compareAndSet("a", "abc")){
                    System.out.println("我是线程：" + Thread.currentThread().getName() + ", 我CAS修改了对象！" + ATOMIC_REFERENCE.get());
                }

                /**
                 * 2、ATOMIC_REFERENCE.getAndSet
                 */
//                System.out.println("我是线程：" + Thread.currentThread().getName() + ", 我修改了对象, 之前的值为: " + ATOMIC_REFERENCE.getAndSet("abc"));
            }).start();
        }
    }

}
