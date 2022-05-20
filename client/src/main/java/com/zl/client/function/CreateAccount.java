package com.zl.client.function;

import com.alibaba.fastjson.JSON;
import com.zl.client.common.Request;
import com.zl.client.common.RequestUtil;
import com.zl.client.common.Response;
import com.zl.common.model.AccountDto;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class CreateAccount extends Function{



    @Override
    public void run() throws ExecutionException, InterruptedException {
        System.out.println("请输入账号密码 zhanghao-mima");
        String s = scanner.nextLine();
        String[] split = s.split("-");
        AccountDto accountDto = new AccountDto();
        accountDto.setUsername(split[0]);
        accountDto.setPassword(split[1]);
        Request request = new Request();
        Future<Response> future = RequestUtil.requestFuture(channel,getCode(),JSON.toJSONString(accountDto).getBytes());
        System.out.println(new String(future.get().getContent()));
    }

    @Override
    public int getCode() {
        return 2;
    }
}
