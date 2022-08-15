package com.jsonyao.cs.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 测试ConcurrentSkipList
 *
 * @author yaocs2
 * @since 2021-07-02
 */
public class UnsafeTest {

    private static Unsafe UNSAFE;
    private static long I_OFFSET;
    private static long J_OFFSET;

    static class Person {
        public int i = 0;
        public int j = 0;
        public String[] table = {"1","2","3","4"};
    }

    static {
        // 测试Unsafe
//        Unsafe unsafe = Unsafe.getUnsafe();// java.lang.SecurityException: Unsafe, 原因: 当前类必须由bootstrap加载器加载, 否则获取unsafe实例会报错
        Field field = null;
        try {
            field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE = (Unsafe) field.get(null);// Object为null, 说明field为static属性
            I_OFFSET = UNSAFE.objectFieldOffset(Person.class.getField("i"));// 获取i变量相对与Person类的偏移量(long类型)
            J_OFFSET = UNSAFE.objectFieldOffset(Person.class.getField("j"));// CAS锁, 保证i相加的原子性
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        testConcurrent();
    }

    public static void testConcurrent() {
        final Person p = new Person();

        // 获取数组每个块的内存大小, 0*ns得到第0块内存的起始地址, 1*ns得到第1块内存的起始地址
        int ns = UNSAFE.arrayIndexScale(String[].class);
        // 获取数组起始偏移
        int base = UNSAFE.arrayBaseOffset(String[].class);
        System.err.println(UNSAFE.getObject(p.table, base + 0 * ns));

//        new Thread(() -> {
//            while (true) {
////                p.i++;// 修改的是线程内存, 会有并发问题
////                System.err.println(p.i);
//
//                // 自旋+CAS原子性更新主内存, 再获取主内存中i的值
//                if (UNSAFE.compareAndSwapInt(p, J_OFFSET, 0, 1)) {// CAS锁, 保证i相加的原子性
//                    UNSAFE.compareAndSwapInt(p, I_OFFSET, p.i, p.i + 1);
//                    System.err.println("1: " + UNSAFE.getIntVolatile(p, I_OFFSET));
//                    p.j = 0;
//                }
//
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//        new Thread(() -> {
//            while (true) {
////                p.i++;// 修改的是线程内存, 会有并发问题
////                System.err.println(p.i);
//
//                // 自旋+CAS原子性更新主内存, 再获取主内存中i的值
//                if (UNSAFE.compareAndSwapInt(p, J_OFFSET, 0, 1)) {// CAS锁, 保证i相加的原子性
//                    UNSAFE.compareAndSwapInt(p, I_OFFSET, p.i, p.i + 1);
//                    System.err.println("2: " + UNSAFE.getIntVolatile(p, I_OFFSET));
//                    p.j = 0;
//                }
//
//                try {
//                    Thread.sleep(300);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }
}
