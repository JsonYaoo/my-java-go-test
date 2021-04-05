package com.jsonyao.cs.note;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimiter implements IRateLimiter {

    private ConcurrentHashMap<String, Integer> countsMap = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, AtomicInteger> atomicCountsMap = new ConcurrentHashMap<>();

    private static final Integer THRESHOLD = 1;

    /**
     * 1、包含IsAllow()
     * 2、请求含有clientId > 100r/1s => 拒绝
     */
    @Override
    public boolean isAllow(String clientId) {
//        synchronized (this) {
            // Integer
//            Integer integer = countsMap.get(clientId);
//            if (integer != null) {
//                integer++;
//                return integer >= THRESHOLD? false : true;
//            } else {
//                countsMap.put(clientId, new Integer(1));
//                return true;
//            }

            // Atomic => 加锁情况下, 有没Atomic都一样
//            AtomicInteger integer = atomicCountsMap.get(clientId);
//            if (integer != null) {
//                return integer.getAndIncrement() >= THRESHOLD? false : true;
//            } else {
//                atomicCountsMap.put(clientId, new AtomicInteger(1));
//                return true;
//            }
//        }

        // Integer => 不加锁的情况下, 不用原子类都可以, 因为是concurrentHashMap是同步获取的
        Integer integer = countsMap.get(clientId);
        if (integer != null) {
            integer++;
            return integer >= THRESHOLD? false : true;
        } else {
            countsMap.put(clientId, new Integer(1));
            return true;
        }

        // Atomic => 不加锁的情况下, 用原子类也可以, 因为都是同步阻塞加值的
//        AtomicInteger integer = atomicCountsMap.get(clientId);
//        if (integer != null) {
//            return integer.getAndIncrement() >= THRESHOLD? false : true;
//        } else {
//            atomicCountsMap.put(clientId, new AtomicInteger(1));
//            return true;
//        }
    }

    public static void main(String[] args) throws InterruptedException {
        final String clientId = UUID.randomUUID().toString();
        RateLimiter rateLimiter = new RateLimiter();
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean result = rateLimiter.isAllow(clientId);
                    System.err.println(Thread.currentThread().getName() + ", result = " + result);
                }
            }).start();
        }

        Thread.sleep(15000);
    }
}

interface IRateLimiter {
    boolean isAllow(String clientId);
}
