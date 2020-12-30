package com.jsonyao.cs.juc.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 测试AtomicIntegerArray
 * 常见的方法列表:
 * @see AtomicIntegerArray#addAndGet(int, int) 执行加法，第一个参数为数组的下标，第二个参数为增加的数量，返回增加后的结果
 * @see AtomicIntegerArray#compareAndSet(int, int, int) 对比修改，参数1：数组下标，参数2：原始值，参数3，修改目标值，修改成功返回true否则false
 * @see AtomicIntegerArray#decrementAndGet(int) 参数为数组下标，将数组对应数字减少1，返回减少后的数据
 * @see AtomicIntegerArray#incrementAndGet(int) 参数为数组下标，将数组对应数字增加1，返回增加后的数据
 *
 * @see AtomicIntegerArray#getAndAdd(int, int) 和addAndGet类似，区别是返回值是变化前的数据
 * @see AtomicIntegerArray#getAndDecrement(int) 和decrementAndGet类似，区别是返回变化前的数据
 * @see AtomicIntegerArray#getAndIncrement(int) 和incrementAndGet类似，区别是返回变化前的数据
 * @see AtomicIntegerArray#getAndSet(int, int) 将对应下标的数字设置为指定值，第二个参数为设置的值，返回是变化前的数据
 */
public class MyAtomicIntegerArrayDemo {

    public static final AtomicIntegerArray ATOMIC_INTEGER_ARRAY = new AtomicIntegerArray(new int[]{1,2,3,4,5,6,7,8,9,10});

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[100];

        for(int i = 0; i < 100; i++){
            final int index = i % 10;
            threads[i] = new Thread(() -> {
                int originValue = ATOMIC_INTEGER_ARRAY.get(index);

                /**
                 * 1、ATOMIC_INTEGER_ARRAY.addAndGet
                 */
//                int result = ATOMIC_INTEGER_ARRAY.addAndGet(index, index+1);
//                System.out.println("线程编号为：" + Thread.currentThread().getName() + " , index:" + index + ", 对应的原始值为：" + originValue + "，增加后的结果为：" + result);

                /**
                 * 2、ATOMIC_INTEGER_ARRAY.compareAndSet
                 */
//                boolean result = ATOMIC_INTEGER_ARRAY.compareAndSet(index, originValue, index+2);
//                System.out.println("线程编号为：" + Thread.currentThread().getName() + " , index:" + index + ", 对应的原始值为：" + originValue + "，设置后的结果为：" + result);

                /**
                 * 3、ATOMIC_INTEGER_ARRAY.decrementAndGet
                 */
//                int result = ATOMIC_INTEGER_ARRAY.decrementAndGet(index);
//                System.out.println("线程编号为：" + Thread.currentThread().getName() + " , index:" + index + ", 对应的原始值为：" + originValue + "，减少后的结果为：" + result);

                /**
                 * 4、ATOMIC_INTEGER_ARRAY.incrementAndGet
                 */
//                int result = ATOMIC_INTEGER_ARRAY.incrementAndGet(index);
//                System.out.println("线程编号为：" + Thread.currentThread().getName() + " , index:" + index + ", 对应的原始值为：" + originValue + "，增加后的结果为：" + result);

                /**
                 * 5、ATOMIC_INTEGER_ARRAY.getAndAdd
                 */
//                int result = ATOMIC_INTEGER_ARRAY.getAndAdd(index, index+1);
//                System.out.println("线程编号为：" + Thread.currentThread().getName() + " , index:" + index + ", 对应的原始值为：" + originValue + "，增加前的结果为：" + result);

                /**
                 * 6、ATOMIC_INTEGER_ARRAY.getAndDecrement
                 */
//                int result = ATOMIC_INTEGER_ARRAY.getAndDecrement(index);
//                System.out.println("线程编号为：" + Thread.currentThread().getName() + " , index:" + index + ", 对应的原始值为：" + originValue + "，减少前的结果为：" + result);

                /**
                 * 7、ATOMIC_INTEGER_ARRAY.getAndIncrement
                 */
//                int result = ATOMIC_INTEGER_ARRAY.getAndIncrement(index);
//                System.out.println("线程编号为：" + Thread.currentThread().getName() + " , index:" + index + ", 对应的原始值为：" + originValue + "，增加前的结果为：" + result);

                /**
                 * 8、ATOMIC_INTEGER_ARRAY.getAndSet
                 */
                int result = ATOMIC_INTEGER_ARRAY.getAndSet(index, index);
                System.out.println("线程编号为：" + Thread.currentThread().getName() + " , index:" + index + ", 对应的原始值为：" + originValue + "，设置前的结果为：" + result);

            });
            threads[i].start();
        }

        for(Thread thread : threads){
            thread.join();
        }

        System.out.println("=========================>\n执行已经完成，结果列表：");
        for(int i = 0 ; i < ATOMIC_INTEGER_ARRAY.length() ; i++) {
            System.out.println(ATOMIC_INTEGER_ARRAY.get(i));
        }
    }

}
