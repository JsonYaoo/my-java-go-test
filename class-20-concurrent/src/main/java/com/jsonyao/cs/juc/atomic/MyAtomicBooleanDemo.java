package com.jsonyao.cs.juc.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 测试AtomicBoolean
 */
public class MyAtomicBooleanDemo {

    /**
     * 主要方法：
     * @see AtomicBoolean#compareAndSet(boolean, boolean)  第一个参数为原始值，第二个参数为要修改的新值，若修改成功则返回true，否则返回false
     * @see AtomicBoolean#getAndSet(boolean)   尝试设置新的boolean值，直到成功为止，返回设置前的数据
     */
    public static final AtomicBoolean ATOMIC_BOOLEAN = new AtomicBoolean();// 默认为false

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++){
           new Thread(() -> {
               try {
                   TimeUnit.MILLISECONDS.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }

               // CAS设置Boolean
               if(ATOMIC_BOOLEAN.compareAndSet(false, true)){
                   System.out.println(Thread.currentThread().getName() + " 成功设置为true!");
               }

               // 因为查出了以前的数据进行CAS, 不成功会一直查询然后CAS, 直到成功
//               System.out.println(Thread.currentThread().getName() + " 成功设置" + ATOMIC_BOOLEAN.getAndSet(true) + "为true!");
           }).start();
        }
    }
}
