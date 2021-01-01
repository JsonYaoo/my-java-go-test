package com.jsonyao.cs.juc.collections;

import java.util.*;
import java.util.concurrent.*;

/**
 * 测试CopyOnWriteArrayList: 可见读场景下效率高, 但写场景效率低
 */
public class MyCopyOnWriteArrayListDemo {

    /**
     * 最大并发数
     */
    public static final int THREAD_COUNT = 64;

    /**
     * List访问数量
     */
    public static final int GET_SIZE = 100000;

    /**
     * List初始化数量
     */
    public static final int SET_SIZE = 1000;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCopyOnWriteArrayListDemo myCopyOnWriteArrayListDemo = new MyCopyOnWriteArrayListDemo();

        /**
         * 测试List查询效率-输出指标:
         * CopyOnWriteArrayList get method cost time is 31
         * Collections.synchronizedList get method cost time is 376
         * vector get method cost time is 360
         */
        myCopyOnWriteArrayListDemo.testGet();

        /**
         * 测试List添加效率-输出指标:
         * CopyOnWriteArrayList add method cost time is 3047
         * Collections.synchronizedList add method cost time is 16
         * vector add method cost time is 31
         */
        myCopyOnWriteArrayListDemo.testAdd();
    }

    /**
     * 测试List查询效率
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void testGet() throws ExecutionException, InterruptedException {
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < GET_SIZE; i++){
            list.add(new Random().nextInt(1000));
        }

        // 线程安全List竞品
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>(list);
        List<Integer> synchronizedList = Collections.synchronizedList(list);
        List<Integer> vector = new Vector<>(list);

        // 初始化测试参数
        int copyOnWriteArrayListRunningTime = 0, synchronziedListRunningTime = 0, vectorRunningTime = 0;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

        // 测试List查询效率
        for(int i = 0; i < THREAD_COUNT; i++){
            copyOnWriteArrayListRunningTime += executorService.submit(new listGetRunningTask(copyOnWriteArrayList, countDownLatch)).get();
        }
        for(int i = 0; i < THREAD_COUNT; i++){
            synchronziedListRunningTime += executorService.submit(new listGetRunningTask(synchronizedList, countDownLatch)).get();
        }
        for(int i = 0; i < THREAD_COUNT; i++){
            vectorRunningTime += executorService.submit(new listGetRunningTask(vector, countDownLatch)).get();
        }

        /**
         * 输出指标:
         * CopyOnWriteArrayList get method cost time is 31
         * Collections.synchronizedList get method cost time is 376
         * vector get method cost time is 360
         */
        countDownLatch.await();
        System.out.println("CopyOnWriteArrayList get method cost time is " + copyOnWriteArrayListRunningTime);
        System.out.println("Collections.synchronizedList get method cost time is " + synchronziedListRunningTime);
        System.out.println("vector get method cost time is " + vectorRunningTime);
        executorService.shutdown();
    }

    /**
     * 测试List添加效率
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void testAdd() throws ExecutionException, InterruptedException {
        List<Integer> list = new ArrayList<>();

        // 线程安全List竞品
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>(list);
        List<Integer> synchronizedList = Collections.synchronizedList(list);
        List<Integer> vector = new Vector<>(list);

        // 初始化测试参数
        int copyOnWriteArrayListRunningTime = 0, synchronziedListRunningTime = 0, vectorRunningTime = 0;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

        // 测试List查询效率
        for(int i = 0; i < THREAD_COUNT; i++){
            copyOnWriteArrayListRunningTime += executorService.submit(new listAddRunningTask(copyOnWriteArrayList, countDownLatch)).get();
        }
        for(int i = 0; i < THREAD_COUNT; i++){
            synchronziedListRunningTime += executorService.submit(new listAddRunningTask(synchronizedList, countDownLatch)).get();
        }
        for(int i = 0; i < THREAD_COUNT; i++){
            vectorRunningTime += executorService.submit(new listAddRunningTask(vector, countDownLatch)).get();
        }

        /**
         * 输出指标:
         * CopyOnWriteArrayList add method cost time is 3047
         * Collections.synchronizedList add method cost time is 16
         * vector add method cost time is 31
         */
        countDownLatch.await();
        System.out.println("CopyOnWriteArrayList add method cost time is " + copyOnWriteArrayListRunningTime);
        System.out.println("Collections.synchronizedList add method cost time is " + synchronziedListRunningTime);
        System.out.println("vector add method cost time is " + vectorRunningTime);
        executorService.shutdown();
    }

    /**
     * 遍历List任务
     */
    class listGetRunningTask implements Callable<Integer> {

        private List<Integer> list;

        private CountDownLatch countDownLatch;

        public listGetRunningTask(List<Integer> list, CountDownLatch countDownLatch) {
            this.list = list;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public Integer call() throws Exception {
            long start = System.currentTimeMillis();

            for(int i = 0; i < list.size(); i++){
                list.get(i);
            }

            long end = System.currentTimeMillis();
            countDownLatch.countDown();
            return (int) (end - start);
        }
    }

    /**
     * 初始化List任务
     */
    class listAddRunningTask implements Callable<Integer> {

        private List<Integer> list;

        private CountDownLatch countDownLatch;

        public listAddRunningTask(List<Integer> list, CountDownLatch countDownLatch) {
            this.list = list;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public Integer call() throws Exception {
            long start = System.currentTimeMillis();

            for(int i = 0; i < SET_SIZE; i++){
                list.add(new Random().nextInt(1000));
            }

            long end = System.currentTimeMillis();
            countDownLatch.countDown();
            return (int) (end - start);
        }
    }
}