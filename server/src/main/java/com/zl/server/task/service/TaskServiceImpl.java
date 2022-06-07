package com.zl.server.task.service;

import com.zl.server.config.ThreadPoolConfig;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.netty.threadpool.Task;
import com.zl.server.netty.threadpool.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;
import java.util.function.BiConsumer;

@Service
public class TaskServiceImpl implements TaskService {

    private static TaskExecutor playerExecutor = ThreadPoolConfig.playerExecutor;
    private static TaskExecutor sceneExecutor = ThreadPoolConfig.sceneExecutor;
    private static ScheduledExecutorService scheduleExecutor = ThreadPoolConfig.scheduleExecutor;

    public <T> void scheduleExecSceneTask(Task task, BiConsumer<? super T, ? super Throwable> action) {
        scheduleExecSceneTask(task, action, 5);
    }

    public <T> void scheduleExecSceneTask(Task task, BiConsumer<? super T, ? super Throwable> action, int delay) {
        CompletableFuture future = new CompletableFuture();
        scheduleExecutor.schedule(() -> {
            if (!future.isCancelled() && !future.isDone()) {
                future.completeExceptionally(new TimeoutException("超时异常"));
            }
        }, delay, TimeUnit.SECONDS);
        sceneExecutor.executeWithFuture(task, future);
        future.whenCompleteAsync(action, sceneExecutor.getExecutorById(task.getId()));
    }

}
