package com.zl.client.handler;

import com.zl.client.common.ChannelHolder;
import com.zl.client.common.Response;
import com.zl.common.common.Constants;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Response response = (Response) msg;
        if (response.getRequestId() == Constants.NOTIFICATIONId) {
            log.info("通知: {}", new String(response.getContent()));
        } else {
            ChannelHolder.completable(response.getRequestId(), response);
        }

    }
}
