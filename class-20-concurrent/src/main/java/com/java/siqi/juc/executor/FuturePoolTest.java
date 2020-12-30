package com.java.siqi.juc.executor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author end
 **/
public class FuturePoolTest implements Callable<String> {

    private int index;

    public FuturePoolTest(int index) {
        this.index = index;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        System.out.println("index:" + index);
        return index + "";
    }

    public static void main(String args[]){
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<String>> list = new ArrayList<>();

        for(int i=0; i< 10; i++){
            Callable<String> callable = new FuturePoolTest(i);
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


