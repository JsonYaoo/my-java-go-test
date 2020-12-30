package juc.collections;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author end
 **/
public class CurrentHashMapTest {

    /**用于测试的线程数量**/
    public static final int threads = 100;

    /**每个线程往map中塞的数量**/
    public static final int NUMBER =100;

    public static void main(String[] args) throws Exception{
        Map<String, Integer> syncHashMap= Collections.synchronizedMap(new HashMap<String, Integer>());
        Map<String, Integer> concurrentHashMap=new ConcurrentHashMap<String, Integer>();
        Hashtable<String, Integer> hashtable=new Hashtable<String, Integer>();

        long totalA = 0;
        long totalB = 0;
        long totalC = 0;

        for (int i = 0; i <= 10; i++) {
            totalA += testPut(syncHashMap);
            totalB += testPut(concurrentHashMap);
            totalC += testPut(hashtable);
        }
        System.out.println("Put time ConcurrentHashMap=" + totalB + "ms.");
        System.out.println("Put time HashMapSync=" + totalA + "ms.");
        System.out.println("Put time Hashtable=" + totalC + "ms.");

        totalA = 0;
        totalB = 0;
        totalC = 0;
        for (int i = 0; i <= 10; i++) {
            totalA += testGet(syncHashMap);
            totalB += testGet(concurrentHashMap);
            totalC += testGet(hashtable);
        }
        System.out.println("Get time ConcurrentHashMap=" + totalB + "ms.");
        System.out.println("Get time HashMapSync=" + totalA + "ms.");
        System.out.println("Get time Hashtable=" + totalC + "ms.");

    }

    private static long testPut(Map<String, Integer> map) throws Exception{
        long start = System.currentTimeMillis();

        //同时开threads个线程
        for (int i = 0; i < threads; i++) {
            new MapPutThread(map).start();
        }
        while (MapPutThread.counter > 0) {
            Thread.sleep(1);
        }
        return System.currentTimeMillis() - start;
    }

    public static long testGet(Map<String, Integer> map) throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < threads; i++) {
            new MapGetThread(map).start();
        }
        while (MapGetThread.counter > 0) {
            Thread.sleep(1);
        }
        return System.currentTimeMillis() - start;
    }
}
/**
 * put线程类
 */
class MapPutThread extends Thread{

    static int counter = 0;//计数器
    static Object lock = new Object();//用于同步的对象锁
    private Map<String, Integer> map;
    private String key = this.getId() + "";

    MapPutThread(Map<String, Integer> map) {
        synchronized (lock) {
            counter++;//每调用一次构建函数，计数器加1
            //     System.out.println("线程key为："+key+"的构造函数运行，当前counter为："+counter);
        }
        this.map = map;
    }


    @Override
    public void run() {
        for (int i = 1; i <= CurrentHashMapTest.NUMBER; i++) {
            map.put(key, i);
            //  System.out.println("线程key为："+key+"的第"+i+"个run方法运行，设置的i为：:"+i);
        }
        synchronized (lock) {
            counter--;//每当往map中put一个值后，计算器减1
            //   System.out.println("线程key为："+key+"的run()运行，当前counter为："+counter);
        }
    }
}
/**
 * get线程类
 */
class MapGetThread extends Thread {

    static int counter = 0;
    static final Object lock = new Object();
    private Map<String, Integer> map;
    private String key = this.getId() + "";

    MapGetThread(Map<String, Integer> map) {
        synchronized (lock) {
            counter++;
        }
        this.map = map;
    }

    @Override
    public void run() {
        for (int i = 1; i <= CurrentHashMapTest.NUMBER; i++) {
            map.get(key);
        }
        synchronized (lock) {
            counter--;
        }
    }
}
