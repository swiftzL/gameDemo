package com.zl.client.function;

import com.alibaba.fastjson.JSON;
import com.zl.client.common.Request;
import com.zl.client.common.RequestUtil;
import com.zl.common.model.AccountDto;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateAccount extends Function{



    @Override
    public void run() {
        System.out.println("请输入账号密码 zhanghao-mima");
        String s = scanner.nextLine();
        String[] split = s.split("-");
        AccountDto accountDto = new AccountDto();
        accountDto.setUsername(split[0]);
        accountDto.setPassword(split[1]);
        Request request = new Request();
        RequestUtil.request(getCode(),JSON.toJSONString(accountDto).getBytes());
        channel.writeAndFlush(request);
    }

    @Override
    public int getCode() {
        return 1;
    }
}
