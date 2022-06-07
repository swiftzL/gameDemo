package com.zl.server.config;

import com.zl.server.commons.Constants;
import com.zl.server.netty.threadpool.DispatchExecutor;
import com.zl.server.netty.threadpool.TaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public interface ThreadPoolConfig {
    TaskExecutor playerExecutor = DispatchExecutor.getExecutor(4, Constants.PlyerThreadName);
    TaskExecutor sceneExecutor = DispatchExecutor.getExecutor(4, Constants.SceneThreadName);
    ScheduledExecutorService scheduleExecutor = Executors.newScheduledThreadPool(1);
}
