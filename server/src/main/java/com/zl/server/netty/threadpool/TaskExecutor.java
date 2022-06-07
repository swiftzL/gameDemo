package com.zl.server.netty.threadpool;

import java.util.concurrent.CompletableFuture;

public interface TaskExecutor {
    void execute(Task task);
    void executeWithFuture(Task task, CompletableFuture future);
}
