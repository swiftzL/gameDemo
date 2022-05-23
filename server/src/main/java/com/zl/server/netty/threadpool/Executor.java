package com.zl.server.netty.threadpool;

public interface Executor {
    void execute(Task task);
}
