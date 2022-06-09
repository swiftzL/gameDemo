package com.zl.server.netty.threadpool;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class Task implements Runnable {
    private int id;
    private Runnable runnable;

    @Override
    public void run() {
        runnable.run();
    }

    public void warp(Runnable runnable) {
        Runnable task = this.runnable;
        this.runnable = () -> {
            task.run();
            runnable.run();
        };
    }


}
