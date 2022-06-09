package com.zl.client.function;

import com.alibaba.fastjson.JSON;
import com.zl.client.common.ChannelHolder;
import com.zl.client.common.Request;
import com.zl.client.common.RequestUtil;
import com.zl.client.common.Response;
import com.zl.common.common.Command;
import com.zl.common.model.AccountDto;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Component
public class LoginAccount extends Function {

    @Override
    public int getCode() {
        return Command.Login.getCode();
    }

    @Override
    public void run() throws ExecutionException, InterruptedException {
        String s = lineReader.readLine("请输入账号密码 zhanghao-mima>");
        String[] split = s.split("-");
        AccountDto accountDto = new AccountDto();
        accountDto.setUsername(split[0]);
        accountDto.setPassword(split[1]);
        RequestUtil.requestFuture(channel, getCode(), JSON.toJSONString(accountDto).getBytes());

    }
}
