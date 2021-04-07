package com.jsonyao.cs.executor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * Thread Local测试类
 *
 * @author yaocs2
 **/
public class FuturePoolTest implements Callable<String> {

    private int index;

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public FuturePoolTest(int index) {
        this.index = index;
    }

    @Override
    public String call() throws Exception {
//        Thread.sleep(1000);
        System.out.println("index:" + index);

        System.err.println(Thread.currentThread().getName() + "设置之前获取: " + threadLocal.get());
        threadLocal.set("123");
        System.err.println(Thread.currentThread().getName() + "设置之后获取: " + threadLocal.get());

        return index + "";
    }

    public static void main(String args[]){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        List<Future<String>> list = new ArrayList<>();

        Callable<String> callable = new FuturePoolTest(10);
        for(int i=0; i< 1000; i++){
            Future<String> future = executor.submit(callable);
            list.add(future);
        }

        for(Future<String> fut : list){
            try {
                System.out.println(new Date()+ "::"+fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }
}


