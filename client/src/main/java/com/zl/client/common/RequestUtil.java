package com.zl.client.common;

import io.netty.channel.Channel;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestUtil {
    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static Request request(int code, byte[] content) {
        Request request = new Request();
        request.setRequestId(atomicInteger.addAndGet(1));
        request.setCommand(code);
        request.setContent(content);
        return request;
    }

    public static void requestFuture(Channel channel, int code, byte[] content){
        Request request = request(code,content);
        channel.writeAndFlush(request);
    }
}
