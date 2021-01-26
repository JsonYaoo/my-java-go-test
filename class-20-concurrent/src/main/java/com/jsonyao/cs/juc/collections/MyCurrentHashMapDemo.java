package com.jsonyao.cs.juc.collections;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 测试CurrentHashMap
 */
public class MyCurrentHashMapDemo {

    /**
     * 最大并发数
     */
    public static final int THREAD_COUNT = 100;

    /**
     * 每个线程往Map中塞数据的总数
     */
    public static final int NUMBER = 100;

    public static void main(String[] args) throws InterruptedException {
        Map<String, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
        Map<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        Map<String, Integer> hashTable = new Hashtable<>();

        // 初始化测试参数
        long totalA = 0, totalB = 0, totalC = 0;

        // 测试10次, 取时间总和
        for(int i = 0; i <= 10; i++){
            totalA += testPut(synchronizedMap);
            totalB += testPut(concurrentHashMap);
            totalC += testPut(hashTable);
        }

        /**
         * 输出指标
         */
        System.out.println("Put time ConcurrentHashMap=" + totalB + "ms.");
        System.out.println("Put time HashMapSync=" + totalA + "ms.");
        System.out.println("Put time Hashtable=" + totalC + "ms.");
    }

    /**
     * Map#put()效率测试
     */
    public static long testPut(Map<String, Integer> map) throws InterruptedException {
        long start = System.currentTimeMillis();

        // 同时开启 THREAD_COUNT 个线程
        for(int i = 0; i < THREAD_COUNT; i++){
            // 开启Map#put测试线程
            new MapPutThread(map).start();
        }
        while (MapPutThread.counter > 0){
            Thread.sleep(1);
        }

        return System.currentTimeMillis() - start;
    }

}

/**
 * Map#put效率测试线程
 */
class MapPutThread extends Thread {

    // 计数器
    static int counter = 0;

    // 测试map
    private Map<String, Integer> map;

    // 测试键
    private String key = this.getId() + "";

    public MapPutThread(Map<String, Integer> map) {
        synchronized (MapPutThread.class) {// 静态变量, 类锁
            counter++;
        }

        this.map = map;
    }

    @Override
    public void run() {
        for(int i = 1; i <= MyCurrentHashMapDemo.NUMBER; i++){
            map.put(key, i);
        }

        synchronized (MapPutThread.class) {// 静态变量, 类锁
            counter--;
        }
    }
}
