package com.zl.server.task.service;

import com.zl.server.config.ThreadPoolConfig;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.netty.threadpool.Task;
import com.zl.server.netty.threadpool.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface TaskService {

    CompletableFuture execSceneTask(Task task);


}
