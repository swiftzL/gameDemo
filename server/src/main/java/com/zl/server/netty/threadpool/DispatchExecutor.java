package com.zl.server.netty.threadpool;

import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
public class DispatchExecutor implements TaskExecutor {
    private Executor[] works;
    private int threadSize;
    private Chooser chooser;

    public static TaskExecutor getExecutor(int threadSize, String threadPre) {
        return new DispatchExecutor(threadSize, new DefaultThreadFactory(threadPre));
    }

    public DispatchExecutor(int threadSize, ThreadFactory threadFactory) {
        this.threadSize = threadSize;
        this.chooser = newChooser(threadSize);
        this.works = new Executor[threadSize];
        startThread(threadFactory);
    }

    private void startThread(ThreadFactory threadFactory) {
        for (int i = 0; i < this.threadSize; i++) {
            Executor executor = new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(), new DefaultThreadFactory("player thread pool"));
            this.works[i] = executor;
        }
    }

    @Override
    public void execute(Task task) {
        works[this.chooser.next(task.getId())].execute(task);
    }

    public void executeWithFuture(Task task, CompletableFuture future) {
        try {
            task.warp(() -> {
                future.complete(null);
            });
            works[this.chooser.next(task.getId())].execute(task);
        } catch (Exception e) {
            future.completeExceptionally(e);
        }
    }

    public Executor getExecutorById(int id) {
        return works[this.chooser.next(id)];
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

}
