package juc.executor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * @author end 2020/12/25 09:41
 **/
public class ForkJoinTest extends RecursiveTask<Long> {

    private long start;
    private long end;

    private static final long LIMIT = 10000L;

    public ForkJoinTest(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;

        if(length < LIMIT){
            long sum = 0L;

            for(long i = start; i<= end; i++){
                sum += i;
            }

            return sum;
        } else {
            long middle = (start + end) / 2;

            ForkJoinTest left = new ForkJoinTest(start, middle);
            left.fork(); //拆分，同时入队

            ForkJoinTest right = new ForkJoinTest(middle + 1, end);
            right.fork(); //拆分，同时入队

            return left.join() + right.join();
        }
    }

    public static void main(String[] args) {
        long num = 5000000000L;

        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinTest(0L, num);
        long sum1 = pool.invoke(task);

        Instant sum1Time = Instant.now();
        System.out.println("forkJoinTask, result:"+ sum1 + ", cost:" + start.until(sum1Time, ChronoUnit.MILLIS));

        long sum2 = 0L;
        for(long i = 1; i<= num; i++){
            sum2 += i;
        }

        Instant sum2Time = Instant.now();
        System.out.println("forTask, result:"+ sum2 + ", cost:" + sum1Time.until(sum2Time, ChronoUnit.MILLIS));

        long sum3 = LongStream.rangeClosed(0L, num).parallel().reduce(0L, Long::sum);
        Instant sum3Time = Instant.now();
        System.out.println("java8Task, result:"+ sum3 + ", cost:" + sum2Time.until(sum3Time, ChronoUnit.MILLIS));

    }
}
