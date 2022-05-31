package com.zl.server.netty.threadpool;

public interface Chooser {
    int next(int id);
}
