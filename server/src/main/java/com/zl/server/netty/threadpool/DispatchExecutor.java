package com.zl.server.netty.threadpool;

import lombok.extern.slf4j.Slf4j;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


@Slf4j
public class DispatchExecutor implements Executor {
    private java.util.concurrent.Executor[] works;
    private int threadSize;
    private Chooser chooser;

    public static class ExecutorHolder {
        public static Executor INSTANCE = new DispatchExecutor(4);
    }

    public DispatchExecutor(int threadSize) {
        this.threadSize = threadSize;
        this.chooser = newChooser(threadSize);
        this.works = new java.util.concurrent.Executor[threadSize];
        startThread();
    }
    private void startThread() {
        for (int i = 0; i < this.threadSize; i++) {
            java.util.concurrent.Executor executor = Executors.newFixedThreadPool(1);
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
}
