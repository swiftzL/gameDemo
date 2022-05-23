package com.zl.server.netty.threadpool;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Task implements Runnable {
    private int id;
    private Runnable runnable;

    @Override
    public void run() {
        runnable.run();
    }
}
