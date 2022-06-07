package com.zl.client.function.scene.facade;

import com.alibaba.fastjson.JSON;
import com.zl.client.common.RequestUtil;
import com.zl.client.function.Function;
import com.zl.common.common.Command;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class QuitScene extends Function {
    @Override
    public int getCode() {
        return Command.QuitScene.getCode();
    }

    @Override
    public void run() throws ExecutionException, InterruptedException {
        RequestUtil.requestFuture(this.channel, getCode(), null);
    }
}
