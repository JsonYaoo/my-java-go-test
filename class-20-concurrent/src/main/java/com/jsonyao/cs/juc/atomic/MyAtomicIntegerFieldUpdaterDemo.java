package com.jsonyao.cs.juc.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 测试AtomicIntegerFieldUpdater
 * 可以直接访问对应的变量，进行修改和处理
 * 条件：要在可访问的区域内，如果是private或挎包访问default类型以及非父亲类的protected均无法访问到
 * 其次访问对象不能是static类型的变量（因为在计算属性的偏移量的时候无法计算），也不能是final类型的变量（因为根本无法修改），必须是普通的成员变量
 *
 * 方法（说明上和AtomicInteger几乎一致，唯一的区别是第一个参数需要传入对象的引用）
 * @see AtomicIntegerFieldUpdater#addAndGet(Object, int)
 * @see AtomicIntegerFieldUpdater#compareAndSet(Object, int, int)
 * @see AtomicIntegerFieldUpdater#decrementAndGet(Object)
 * @see AtomicIntegerFieldUpdater#incrementAndGet(Object)
 *
 * @see AtomicIntegerFieldUpdater#getAndAdd(Object, int)
 * @see AtomicIntegerFieldUpdater#getAndDecrement(Object)
 * @see AtomicIntegerFieldUpdater#getAndIncrement(Object)
 * @see AtomicIntegerFieldUpdater#getAndSet(Object, int)
 */
public class MyAtomicIntegerFieldUpdaterDemo {

    static class A {
        volatile int intValue = 100;
    }

    public static final AtomicIntegerFieldUpdater<A> ATOMIC_INTEGER_FIELD_UPDATER = AtomicIntegerFieldUpdater.newUpdater(A.class, "intValue");

    public static void main(String[] args) {
        final A a = new A();
        for(int i = 0; i < 10; i++){
            new Thread(() -> {
                /**
                 * 1、ATOMIC_INTEGER_FIELD_UPDATER.addAndGet
                 */
//                int result = ATOMIC_INTEGER_FIELD_UPDATER.addAndGet(a, 10);
//                System.out.println("我是线程：" + Thread.currentThread().getName() + " 增加了10, 增加后的结果为: " + result);

                /**
                 * 2、ATOMIC_INTEGER_FIELD_UPDATER.compareAndSet
                 */
//                boolean result = ATOMIC_INTEGER_FIELD_UPDATER.compareAndSet(a, 100, 120);
//                System.out.println("我是线程：" + Thread.currentThread().getName() + " 使用CAS设置了值, 设置结果为: " + result);

                /**
                 * 3、ATOMIC_INTEGER_FIELD_UPDATER.decrementAndGet
                 */
//                int result = ATOMIC_INTEGER_FIELD_UPDATER.decrementAndGet(a);
//                System.out.println("我是线程：" + Thread.currentThread().getName() + " 减少了1, 减少后的结果为: " + result);

                /**
                 * 4、ATOMIC_INTEGER_FIELD_UPDATER.incrementAndGet
                 */
//                int result = ATOMIC_INTEGER_FIELD_UPDATER.incrementAndGet(a);
//                System.out.println("我是线程：" + Thread.currentThread().getName() + " 增加了1, 增加后的结果为: " + result);

                /**
                 * 5、ATOMIC_INTEGER_FIELD_UPDATER.getAndAdd
                 */
//                int result = ATOMIC_INTEGER_FIELD_UPDATER.getAndAdd(a, 10);
//                System.out.println("我是线程：" + Thread.currentThread().getName() + " 增加了10, 增加前的结果为: " + result);

                /**
                 * 6、ATOMIC_INTEGER_FIELD_UPDATER.getAndDecrement
                 */
//                int result = ATOMIC_INTEGER_FIELD_UPDATER.getAndDecrement(a);
//                System.out.println("我是线程：" + Thread.currentThread().getName() + " 减少了1, 减少前的结果为: " + result);

                /**
                 * 7、ATOMIC_INTEGER_FIELD_UPDATER.getAndIncrement
                 */
//                int result = ATOMIC_INTEGER_FIELD_UPDATER.getAndIncrement(a);
//                System.out.println("我是线程：" + Thread.currentThread().getName() + " 增加了1, 增加前的结果为: " + result);

                /**
                 * 8、ATOMIC_INTEGER_FIELD_UPDATER.getAndSet
                 */
                int result = ATOMIC_INTEGER_FIELD_UPDATER.getAndSet(a, 120);
                System.out.println("我是线程：" + Thread.currentThread().getName() + " 设置为了120, 设置前的结果为: " + result);
            }).start();
        }
    }
}
