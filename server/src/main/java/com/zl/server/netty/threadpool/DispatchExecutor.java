package com.zl.server.netty.threadpool;

import lombok.extern.slf4j.Slf4j;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
public class DispatchExecutor implements TaskExecutor {
    private Executor[] works;
    private int threadSize;
    private Chooser chooser;
    private ThreadFactory threadFactory = new PlayThreadFactory();

    public static class ExecutorHolder {
        public static TaskExecutor INSTANCE = new DispatchExecutor(4);
    }

    public DispatchExecutor(int threadSize) {
        this.threadSize = threadSize;
        this.chooser = newChooser(threadSize);
        this.works = new Executor[threadSize];
        startThread();
    }

    private void startThread() {
        for (int i = 0; i < this.threadSize; i++) {
            Executor executor = new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(), this.threadFactory);
            this.works[i] = executor;
        }
    }

    @Override
    public void execute(Task task) {
        works[this.chooser.next(task.getId())].execute(task);
    }

    private static boolean isPowerOfTwo(int val) {
        return (val & -val) == val;
    }

    public Chooser newChooser(int threadSize) {
        if (isPowerOfTwo(threadSize)) {
            return (id) -> id & (threadSize - 1);
        } else {
            return (id) -> id % threadSize;
        }
    }

    static class PlayThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        PlayThreadFactory() {
            namePrefix = "play thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(null, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            return t;
        }
    }
}
