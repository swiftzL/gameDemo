package com.zl.server.handler;

import com.alibaba.fastjson.JSON;
import com.zl.server.commons.Request;
import com.zl.server.commons.Response;
import com.zl.server.config.NetMessageProcessor;
import com.zl.server.netty.Invoke;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;

public class ServiceHandler extends ChannelInboundHandlerAdapter {

    private Map<Integer, Invoke> invokes = NetMessageProcessor.invokes;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Request request = (Request) msg;
        Response.setRequestId(request.getRequestId()); //设置请求id
        Invoke invoke = invokes.get(request.getCommand());
        if (invoke == null) {
            ctx.channel().writeAndFlush(Response.err("指令错误"));
            return;
        }
        Class[] argsType = invoke.getArgsType();
        Object[] args = new Object[argsType.length];
        for (int i = 0; i < args.length; i++) {
            Class clazz = argsType[i];
            if (Channel.class.equals(clazz)) {
                args[i] = ctx.channel();
            } else if (Request.class.equals(clazz)) {
                args[i] = request;
            } else {
                args[i] = JSON.parseObject(request.getContent(), clazz);
            }
        }
        Object obj = invoke.invoke(args);
        if (invoke.isVoid()) {
            return;
        }
        Response response;
        if(invoke.returnIsResponse()){
            response = (Response) obj;
        }else {
            response = new Response();
            response.setStatusCode(200);
            response.setContent(JSON.toJSONString(obj).getBytes());
        }
        response.setRequestId(request.getRequestId());
        ctx.channel().writeAndFlush(response);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}
