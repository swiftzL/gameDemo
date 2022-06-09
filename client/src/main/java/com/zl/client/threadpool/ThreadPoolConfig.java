package com.zl.client.threadpool;

import com.zl.common.common.Constants;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public interface ThreadPoolConfig {

    ScheduledExecutorService scheduleExecutor = Executors.newScheduledThreadPool(1, new DefaultThreadFactory(Constants.ScheduledThreadName));
}


