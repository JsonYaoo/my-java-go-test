package juc.collections;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author end
 **/
public class CopyOnWriteArrayListTest {

    /**
     * 并发数
     */
    public final static int THREAD_COUNT = 64;
    /**
     * list大小
     */
    public final static int SIZE = 100000;

    /**
     * 测试读性能
     *
     * @throws Exception
     */
    public void testGet() throws Exception {
        List<Integer> list = initList();
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>(list);
        List<Integer> synchronizedList = Collections.synchronizedList(list);
        Vector vector = new Vector(list);

        int copyOnWriteArrayListTime = 0;
        int synchronizedListTime = 0;
        int vectorTime = 0;
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            copyOnWriteArrayListTime += executor.submit(new CopyOnWriteTestGetTask(copyOnWriteArrayList, countDownLatch)).get();
        }
        System.out.println("CopyOnWriteArrayList get method cost time is " + copyOnWriteArrayListTime);

        for (int i = 0; i < THREAD_COUNT; i++) {
            synchronizedListTime += executor.submit(new CopyOnWriteTestGetTask(synchronizedList, countDownLatch)).get();
        }
        System.out.println("Collections.synchronizedList get method cost time is " + synchronizedListTime);

        for (int i = 0; i < THREAD_COUNT; i++) {
            vectorTime += executor.submit(new CopyOnWriteTestGetTask(vector, countDownLatch)).get();
        }
        System.out.println("vector get method cost time is " + vectorTime);
    }

    public void testAdd() throws Exception {
        List<Integer> list = new ArrayList<>();
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>(list);
        List<Integer> synchronizedList = Collections.synchronizedList(list);
        Vector vector = new Vector(list);

        int copyOnWriteArrayListTime = 0;
        int synchronizedListTime = 0;
        int vectorTime = 0;
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            copyOnWriteArrayListTime += executor.submit(new CopyOnWriteTestAddTask(copyOnWriteArrayList, countDownLatch)).get();
        }
        System.out.println("CopyOnWriteArrayList add method cost time is " + copyOnWriteArrayListTime);

        for (int i = 0; i < THREAD_COUNT; i++) {
            synchronizedListTime += executor.submit(new CopyOnWriteTestAddTask(synchronizedList, countDownLatch)).get();
        }
        System.out.println("Collections.synchronizedList add method cost time is " + synchronizedListTime);

        for (int i = 0; i < THREAD_COUNT; i++) {
            vectorTime += executor.submit(new CopyOnWriteTestAddTask(vector, countDownLatch)).get();
        }
        System.out.println("vector add method cost time is " + vectorTime);
    }

    private List<Integer> initList() {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < SIZE; i++) {
            list.add(new Random().nextInt(1000));
        }
        return list;
    }

    class CopyOnWriteTestGetTask implements Callable<Integer> {
        List<Integer> list;
        CountDownLatch countDownLatch;

        CopyOnWriteTestGetTask(List<Integer> list, CountDownLatch countDownLatch) {
            this.list = list;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public Integer call() {
            int pos = new Random().nextInt(SIZE);
            long start = System.currentTimeMillis();
            for (int i = 0; i < SIZE; i++) {
                list.get(pos);
            }
            long end = System.currentTimeMillis();
            countDownLatch.countDown();
            return (int) (end - start);
        }
    }

    class CopyOnWriteTestAddTask implements Callable<Integer> {
        List<Integer> list;
        CountDownLatch countDownLatch;

        CopyOnWriteTestAddTask(List<Integer> list, CountDownLatch countDownLatch) {
            this.list = list;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public Integer call() {
            long start = System.currentTimeMillis();
            for (int i = 0; i < SIZE; i++) {
                list.add(new Random().nextInt(1000));
            }
            long end = System.currentTimeMillis();
            countDownLatch.countDown();
            return (int) (end - start);
        }
    }

    public static void main(String[] args) {
        CopyOnWriteArrayListTest arrayListDemo = new CopyOnWriteArrayListTest();
        try {
            arrayListDemo.testGet();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            arrayListDemo.testAdd();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
