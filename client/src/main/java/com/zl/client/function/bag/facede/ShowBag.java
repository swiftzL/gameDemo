package com.zl.client.function.bag.facede;

import com.alibaba.fastjson.JSON;
import com.zl.client.common.RequestUtil;
import com.zl.client.common.Response;
import com.zl.client.function.Function;
import com.zl.client.function.bag.model.MS_Props;
import com.zl.common.common.Command;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class ShowBag extends Function {
    @Override
    public int getCode() {
        return Command.ShowBag.getCode();
    }

    @Override
    public void run() throws ExecutionException, InterruptedException {
        System.out.println("查询中");
        RequestUtil.requestFuture(this.channel, getCode(), null);
    }
}
