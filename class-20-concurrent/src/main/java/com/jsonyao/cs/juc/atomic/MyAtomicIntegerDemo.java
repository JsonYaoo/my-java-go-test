package com.jsonyao.cs.juc.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试AtomicInteger
 * a. 常见的方法列表:
 * @see AtomicInteger#get()             直接返回值
 * @see AtomicInteger#getAndAdd(int)    增加指定的数据，返回变化前的数据
 * @see AtomicInteger#getAndDecrement() 减少1，返回减少前的数据
 * @see AtomicInteger#getAndIncrement() 增加1，返回增加前的数据
 * @see AtomicInteger#getAndSet(int)    设置指定的数据，返回设置前的数据
 *
 * @see AtomicInteger#addAndGet(int)    增加指定的数据后返回增加后的数据
 * @see AtomicInteger#decrementAndGet() 减少1，返回减少后的值
 * @see AtomicInteger#incrementAndGet() 增加1，返回增加后的值
 * @see AtomicInteger#lazySet(int)      仅仅当get时才会set
 *
 * @see AtomicInteger#compareAndSet(int, int) 尝试新增后对比，若增加成功则返回true否则返回false
 */
public class MyAtomicIntegerDemo {

    public static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];

        for(int i = 0; i < 10; i++){
            threads[i] = new Thread(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /**
                 * 2、ATOMIC_INTEGER.getAndAdd()
                 */
//                System.out.println("我是线程：" + Thread.currentThread().getName() + ", 我增加了2, 变化前的值为: " + ATOMIC_INTEGER.getAndAdd(2));

                /**
                 * 3、ATOMIC_INTEGER.getAndDecrement()
                 */
//                System.out.println("我是线程：" + Thread.currentThread().getName() + ", 我减少了1, 变化前的值为: " + ATOMIC_INTEGER.getAndDecrement());

                /**
                 * 4、ATOMIC_INTEGER.getAndIncrement()
                 */
//                System.out.println("我是线程：" + Thread.currentThread().getName() + ", 我增加了1, 变化前的值为: " + ATOMIC_INTEGER.getAndIncrement());

                /**
                 * 5、ATOMIC_INTEGER.addAndGet()
                 */
//                System.out.println("我是线程：" + Thread.currentThread().getName() + ", 我增加了2, 变化后的值为: " + ATOMIC_INTEGER.addAndGet(2));

                /**
                 * 6、ATOMIC_INTEGER.decrementAndGet()
                 */
//                System.out.println("我是线程：" + Thread.currentThread().getName() + ", 我减少了1, 变化后的值为: " + ATOMIC_INTEGER.decrementAndGet());

                /**
                 * 7、ATOMIC_INTEGER.incrementAndGet()
                 */
//                System.out.println("我是线程：" + Thread.currentThread().getName() + ", 我增加了1, 增加后的值为: " + ATOMIC_INTEGER.incrementAndGet());

                /**
                 * 8、ATOMIC_INTEGER.lazySet()
                 */
//                ATOMIC_INTEGER.lazySet(3);
//                System.out.println("我是线程：" + Thread.currentThread().getName() + ", 我延迟设置为3, 设置后的值为: " + ATOMIC_INTEGER.get());

                /**
                 * 7、ATOMIC_INTEGER.compareAndSet()
                 */
                System.out.println("我是线程：" + Thread.currentThread().getName() + ", 我打算CAS为4, 设置结果为: " + ATOMIC_INTEGER.compareAndSet(0, 4));
            });
            threads[i].start();
        }

        // join()的作用是："等待该线程终止": 主线程等待子线程的终止 => 对于主线程, 在子线程调用了join()方法后面的代码，只有等到子线程结束了才能执行
        for(Thread thread : threads){
            thread.join();
        }

        /**
         * 1、ATOMIC_INTEGER.get()
         */
        System.out.println("最终运行结果：" + ATOMIC_INTEGER.get());
    }
}
