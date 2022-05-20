package com.zl.client.function;

import com.alibaba.fastjson.JSON;
import com.zl.client.common.ChannelHolder;
import com.zl.client.common.Request;
import com.zl.client.common.RequestUtil;
import com.zl.client.common.Response;
import com.zl.common.model.AccountDto;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Component
public class LoginAccount extends Function {

    @Override
    public int getCode() {
        return 1;
    }

    @Override
    public void run() throws ExecutionException, InterruptedException {
        System.out.println("请输入账号密码 zhanghao-mima");
        String s = scanner.nextLine();
        String[] split = s.split("-");
        AccountDto accountDto = new AccountDto();
        accountDto.setUsername(split[0]);
        accountDto.setPassword(split[1]);
        Request request = RequestUtil.request(getCode(), JSON.toJSONString(accountDto).getBytes());
        CompletableFuture<Response> future = new CompletableFuture();
        ChannelHolder.put(request.getRequestId(), future);
        channel.writeAndFlush(request);
        Response response = future.get();
        System.out.println(new String(response.getContent()));

    }
}
