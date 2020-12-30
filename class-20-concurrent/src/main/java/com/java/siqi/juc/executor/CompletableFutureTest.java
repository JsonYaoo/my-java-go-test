package com.java.siqi.juc.executor;

import java.util.concurrent.CompletableFuture;

/**
 * @author end 2020/12/25 15:55
 **/
public class CompletableFutureTest {

    public static void main(String[] args) throws Exception{
//        CompletableFuture<Integer> intFuture = CompletableFuture.completedFuture(100);
//        // 100
//        System.out.println(intFuture.get());
//
//        CompletableFuture<Void> voidFuture = CompletableFuture.runAsync(() -> System.out.println("hello"));
//        // null
//        System.out.println(voidFuture.get());
//
//        CompletableFuture<String> stringFuture = CompletableFuture.supplyAsync(() -> "hello");
//        // hello
//        System.out.println(stringFuture.get());

        // whenComplete
//        CompletableFuture whenFuture = CompletableFuture.supplyAsync(() -> {
//            return "hello";
//        }).whenComplete((v, e) -> {
//            // hello
//            System.out.println(v);
//        });
//        // hello
//        System.out.println(whenFuture.get());


        // 转换，消费，执行
//        CompletableFuture.supplyAsync(() -> {
//            return "hello ";
//        }).thenAccept(str -> {
//            // hello world
//            System.out.println(str + "world");
//        }).thenRun(() -> {
//            // task finish
//            System.out.println("task finish");
//        });
//
//        // 组合（两个任务都完成）
//        CompletableFuture combineFuture = CompletableFuture.supplyAsync(() -> {
//            return "hello ";
//        }).thenApply(t -> {
//            return t + "world ";
//        }).thenCombine(CompletableFuture.completedFuture("java "), (t, u) -> {
//            return t + u;
//        }).whenComplete((t, e) -> {
//            System.out.println(t);
//        });
//
//        // 组合（只需要一个任务完成）
//        CompletableFuture<String> eitherFuture1 = CompletableFuture.supplyAsync(() -> {
//            return "one";
//        });
//        CompletableFuture eitherFuture2 = CompletableFuture.supplyAsync(() -> {
//            return "tow";
//        });
//        CompletableFuture future = eitherFuture1.applyToEither(eitherFuture2, str -> str);
//        System.out.println(future.get());
//
//        // 多任务组合
//        CompletableFuture<String> allOfFuture1 = CompletableFuture.supplyAsync(() -> {
//            return "one";
//        });
//        CompletableFuture<String> allOfFuture2 = CompletableFuture.supplyAsync(() -> {
//            return "tow";
//        });
//        CompletableFuture<String> allOfFuture3 = CompletableFuture.supplyAsync(() -> {
//            return "three";
//        });
//
//        // allOf
//        CompletableFuture.allOf(allOfFuture1, allOfFuture2, allOfFuture3)
//                .thenApply(v -> Stream.of(allOfFuture1, allOfFuture2, allOfFuture3)
//                                .map(CompletableFuture::join)
//                                .collect(Collectors.joining(" ")))
//                .thenAccept(System.out::println);
//
//        // anyOf
//        CompletableFuture<Object> resultFuture = CompletableFuture.anyOf(allOfFuture1, allOfFuture2, allOfFuture3);
//        System.out.println(resultFuture.get());
//
        // 异常
        CompletableFuture<Integer> exceptionFuture = CompletableFuture.supplyAsync(() -> {
            return 100 / 0;
        }).thenApply(num -> {
            return num + 10;
        }).exceptionally(throwable -> {
            return 0;
        });
            // 0
        System.out.println(exceptionFuture.get());
    }
}
