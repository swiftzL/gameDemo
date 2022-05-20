package com.zl.client.handler;

import com.zl.client.common.ChannelHolder;
import com.zl.client.common.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Response response = (Response) msg;
        ChannelHolder.completable(response.getRequestId(),response);
    }
}
