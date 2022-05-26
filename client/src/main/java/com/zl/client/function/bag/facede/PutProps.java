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
public class PutProps extends Function {
    @Override
    public int getCode() {
        return Command.PutProps.getCode();
    }

    @Override
    public void run() throws ExecutionException, InterruptedException {
        System.out.println("输入道具编号-道具数量");
        String s = scanner.nextLine();
        MS_Props ms_props = new MS_Props();
        String[] split = s.split("-");
        ms_props.setPropsId(Integer.valueOf(split[0]));
        ms_props.setNum(Integer.valueOf(split[1]));
        Future<Response> responseFuture = RequestUtil.requestFuture(this.channel, getCode(), JSON.toJSONString(ms_props).getBytes());
        System.out.println(new String(responseFuture.get().getContent()));

    }
}