package com.zl.server.netty.threadpool;

import lombok.extern.slf4j.Slf4j;


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
        private volatile boolean running = true;
        private volatile Runnable task = null;

        @Override
        public void run() {
            while (running) {
                synchronized (this) {
                    if (task == null) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        task.run();
                        task = null;
                    } catch (Exception e) {
                        log.error("job exception" + e);
                    }
                }


            }
        }

        private void addTask(Runnable task) {
            synchronized (this) {
                this.task = task;
                this.notifyAll();
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
            return (id) -> id & (threadSize - 1);
        } else {
            return (id) -> id % threadSize;
        }
    }


}
