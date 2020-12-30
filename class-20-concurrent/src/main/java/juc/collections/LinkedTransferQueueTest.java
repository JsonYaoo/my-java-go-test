package juc.collections;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;

/**
 * @author end 2020/12/26 10:53
 **/
public class LinkedTransferQueueTest {
    static LinkedTransferQueue<String> lnkTransQueue = new LinkedTransferQueue<String>();
    public static void main(String[] args) {
        ExecutorService exService = Executors.newFixedThreadPool(2);
        Producer producer = new LinkedTransferQueueTest().new Producer();
        Consumer consumer = new LinkedTransferQueueTest().new Consumer();
        exService.execute(producer);
        exService.execute(consumer);
        exService.shutdown();
    }

    class Producer implements Runnable{
        @Override
        public void run() {
            for(int i=0;i<3;i++){
                try {
                    System.out.println("Producer is waiting to transfer...");
                    lnkTransQueue.transfer("A"+i);
                    System.out.println("producer transfered element: A"+i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Consumer implements Runnable{
        @Override
        public void run() {
            for(int i=0;i<3;i++){
                try {
                    System.out.println("Consumer is waiting to take element...");
                    String s= lnkTransQueue.take();
                    System.out.println("Consumer received Element: "+s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
