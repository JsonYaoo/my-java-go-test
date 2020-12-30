package com.java.siqi.juc.executor;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author end 2020/12/25 15:39
 **/
public class FutureTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int[] a = new int[2000];
        Random rd = new Random();
        for(int i = 0; i < 2000; i++){
            a[i] = rd.nextInt(20000);
        }

        class FindMax implements Callable<Integer> {
            private int begin, end;
            private int a[];
            public FindMax(int a[],int begin, int end){
                this.a = a;
                this.begin = begin;
                this.end = end;
            }
            @Override
            public Integer call() throws Exception {
                int maxInPart = a[begin];
                for(int i = begin; i <= end; i++){
                    if(a[i] > maxInPart){
                        maxInPart = a[i];
                    }
                }
                return new Integer(maxInPart);
            }
        }

        FutureTask<Integer> findMaxInFirstPart =
                new FutureTask<Integer>(new FindMax(a,0,999));
        FutureTask<Integer> findMaxInSecondPart =
                new FutureTask<Integer>(new FindMax(a,1000,1999));

        new Thread(findMaxInFirstPart).start();
        new Thread(findMaxInSecondPart).start();

        int maxInFirst =  (int) findMaxInFirstPart.get();
        int maxInSecond = (int) findMaxInSecondPart.get();
        System.out.println("Max is " +
                (maxInFirst > maxInSecond ? maxInFirst : maxInSecond));

        //验证结果是否正确
        int max = a[0];
        for(int i = 0; i < 2000; i++){
            if(a[i] > max){
                max = a[i];
            }
        }
        System.out.println(max);
    }
}
