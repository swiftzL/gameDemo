package com.zl.server.netty.threadpool;

public interface TaskExecutor {
    void execute(Task task);
}
