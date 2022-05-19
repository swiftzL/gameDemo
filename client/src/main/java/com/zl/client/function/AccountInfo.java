package com.zl.client.function;

import com.zl.client.common.Request;
import com.zl.client.common.RequestUtil;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.springframework.stereotype.Component;

@Component
public class AccountInfo extends Function{
    @Override
    int getCode() {
        return 3;
    }

    @Override
    public void run() {
        System.out.println("查询中.....");
        Request request = RequestUtil.request(3, null);
        channel.writeAndFlush(request);
    }
}
