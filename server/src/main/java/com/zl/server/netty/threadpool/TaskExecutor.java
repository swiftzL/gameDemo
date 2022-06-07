package com.zl.server.netty.threadpool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public interface TaskExecutor {
    void execute(Task task);
    void executeWithFuture(Task task, CompletableFuture future);
    Executor getExecutorById(int id);
}
