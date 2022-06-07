package com.zl.server.config;

import com.zl.server.commons.Constants;
import com.zl.server.netty.threadpool.DispatchExecutor;
import com.zl.server.netty.threadpool.TaskExecutor;

public interface ThreadPoolConfig {
    TaskExecutor playerExecutor = DispatchExecutor.getExecutor(4, Constants.PlyerThreadName);
    TaskExecutor sceneExecutor = DispatchExecutor.getExecutor(4, Constants.SceneThreadName);
}
