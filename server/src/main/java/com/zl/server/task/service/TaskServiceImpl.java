package com.zl.server.task.service;

import com.zl.server.config.ThreadPoolConfig;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.netty.threadpool.Task;
import com.zl.server.netty.threadpool.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class TaskServiceImpl implements TaskService {

    private static TaskExecutor playerExecutor = ThreadPoolConfig.playerExecutor;
    private static TaskExecutor sceneExecutor = ThreadPoolConfig.sceneExecutor;

    public CompletableFuture execSceneTask(Task task) {
        CompletableFuture future = new CompletableFuture();
        sceneExecutor.executeWithFuture(task, future);
        return future;
    }
}
