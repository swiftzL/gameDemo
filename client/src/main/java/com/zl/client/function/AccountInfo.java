package com.zl.client.function;

import com.zl.client.common.Request;
import com.zl.client.common.RequestUtil;
import com.zl.client.common.Response;
import com.zl.common.common.Command;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class AccountInfo extends Function {
    @Override
    public int getCode() {
        return Command.AccountInfo.getCode();
    }

    @Override
    public void run() throws ExecutionException, InterruptedException {
        System.out.println("查询中.....>");
        RequestUtil.requestFuture(channel, getCode(), null);
    }
}
