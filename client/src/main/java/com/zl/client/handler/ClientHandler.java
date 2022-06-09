package com.zl.client.handler;

import com.zl.client.common.ChannelHolder;
import com.zl.client.common.Request;
import com.zl.client.common.RequestUtil;
import com.zl.client.common.Response;
import com.zl.client.threadpool.ThreadPoolConfig;
import com.zl.common.common.Command;
import com.zl.common.common.Constants;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private boolean active = false;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Response response = (Response) msg;
        if (response.getRequestId() != Constants.NOTIFICATIONId && response.getCommand() == Command.Heartbeat.getCode()) {//心跳
            active = true;
            return;
        }
        if (response.getRequestId() == Constants.NOTIFICATIONId) {
            System.out.println(new String(response.getContent()));
        } else {
            ChannelHolder.completable(response.getRequestId(), response);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.executor().scheduleAtFixedRate(() -> {
            active = false;
            ctx.channel().writeAndFlush(RequestUtil.request(Command.Heartbeat.getCode(), null));
        }, 0, 10, TimeUnit.SECONDS);
//        ThreadPoolConfig.scheduleExecutor.scheduleAtFixedRate(() -> {
//            if (!active) {
//                System.out.println("退出");
//                System.exit(0);
//            }
//        }, 15, 15, TimeUnit.SECONDS);
        super.channelRegistered(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println(evt);
        if (evt instanceof IdleStateEvent) {
            System.out.println(evt);
            System.out.println("超时");
            System.exit(0);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("退出");
        System.exit(0);
        super.channelUnregistered(ctx);
    }
}
