package com.zl.client.function.base;

import com.zl.client.common.RequestUtil;
import com.zl.client.function.Function;
import com.zl.common.common.Command;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class Logout extends Function {
    @Override
    public int getCode() {
        return Command.LOGOUT.getCode();
    }

    @Override
    public void run() throws ExecutionException, InterruptedException {
        RequestUtil.requestFuture(this.channel, getCode(), null);
        TimeUnit.SECONDS.sleep(2);
        if (!channel.isActive()) {
            System.out.println("退出完成");
        }
    }
}
