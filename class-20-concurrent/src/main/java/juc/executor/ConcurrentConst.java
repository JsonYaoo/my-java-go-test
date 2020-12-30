package juc.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并发配置常量
 *
 */
public final class ConcurrentConst {

    private ConcurrentConst() {}

    public static class ConcurrentExecutor extends ThreadPoolExecutor {

        public ConcurrentExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        }

        @Override
        public void execute(Runnable command) {
            super.execute(() -> {
                command.run();
            });
        }

        private boolean isCsaThread() {
            return Thread.currentThread() instanceof ConcurrentExecuteThread;
        }
    }

    public static ThreadFactory getThreadFactory() {
        return new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new ConcurrentExecuteThread(r,
                    "Traced Thread #" + mCount.getAndIncrement());
            }
        };
    }

    public static ThreadFactory getNamedThreadFactory(String prefix) {
        return new NamedThreadFactory(prefix);
    }

    private static class NamedThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNum;
        private final String prefix;
        private final boolean daemon;
        private final ThreadGroup group;

        public NamedThreadFactory(String prefix) {
            this(prefix, false);
        }

        public NamedThreadFactory(String prefix, boolean daemon) {
            this.threadNum = new AtomicInteger(1);
            this.prefix = prefix + "-thread-";
            this.daemon = daemon;
            SecurityManager s = System.getSecurityManager();
            this.group = s == null ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
        }

        @Override
        public Thread newThread(Runnable runnable) {
            String name = this.prefix + this.threadNum.getAndIncrement();
            Thread ret = new Thread(this.group, runnable, name, 0L);
            ret.setDaemon(this.daemon);
            return ret;
        }

        public ThreadGroup getThreadGroup() {
            return this.group;
        }
    }


    private static class ConcurrentExecuteThread extends Thread {
        ConcurrentExecuteThread(Runnable target, String name) {
            super(target, name);
        }
    }
}
