package com.zl.server.netty.threadpool;

import io.netty.util.concurrent.DefaultEventExecutorChooserFactory;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorChooserFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
public class DispatchExecutor implements Executor {
    private Work[] works;
    private int threadSize;
    private Chooser chooser;

    public static class ExecutorHolder {
        public static Executor INSTANCE = new DispatchExecutor(4);
    }

    public DispatchExecutor(int threadSize) {
        this.threadSize = threadSize;
        this.works = new Work[threadSize];
        this.chooser = newChooser(threadSize);
        startThread();
    }


    private void startThread() {
        for (int i = 0; i < this.threadSize; i++) {
            Work work = new Work();
            this.works[i] = work;
            Thread thread = new Thread(work, "dispatch thread - " + i);
            thread.start();
        }
    }

    @Override
    public void execute(Task task) {
        works[this.chooser.next(task.getId())].addTask(task);
    }

    class Work implements Runnable {
        private Deque<Runnable> deque = new LinkedList<>();
        private volatile boolean running = true;

        @Override
        public void run() {
            Runnable task = null;
            while (running) {
                while (deque.isEmpty()) {
                    synchronized (deque) {
                        try {
                            deque.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                }
                task = deque.pollFirst();

                try {
                    task.run();
                } catch (Exception e) {
                    log.error("job exception" + e);
                }
            }
        }

        private void addTask(Runnable task) {
            synchronized (deque) {
                this.deque.addLast(task);
                this.deque.notifyAll();
            }
        }

        public void shutdown() {
            this.running = false;
        }
    }

    private static boolean isPowerOfTwo(int val) {
        return (val & -val) == val;
    }

    public Chooser newChooser(int threadSize) {
        if (isPowerOfTwo(threadSize)) {
            return (id) -> id & threadSize - 1;
        } else {
            return (id) -> id % threadSize;
        }
    }


}
