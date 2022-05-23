package com.zl.server.netty;

import com.alibaba.fastjson.JSON;
import com.zl.common.message.NetMessage;
import com.zl.server.commons.Request;
import com.zl.server.commons.Response;
import com.zl.server.config.NetMessageProcessor;
import com.zl.server.play.base.packet.MR_Response;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServiceHandler extends ChannelInboundHandlerAdapter {

    private Map<Integer, Invoke> invokes = NetMessageProcessor.invokes;
    private AttributeKey<NetConnection> netConnection = AttributeKey.valueOf("net_connection");
    private Executor executor = new ThreadPoolExecutor(4, 4, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        executor.execute(() -> {
            try {
                exec(ctx.channel(), msg);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private void exec(Channel channel, Object msg) throws InvocationTargetException, IllegalAccessException {
        Request request = (Request) msg;
        NetConnection connection = getConnection(channel);
        Invoke invoke = invokes.get(request.getCommand());
        if (invoke == null) {
            connection.sendMessage(new MR_Response("指令错误"));
            return;
        }
        Class[] argsType = invoke.getArgsType();
        Object[] args = new Object[argsType.length];
        for (int i = 0; i < args.length; i++) {
            Class clazz = argsType[i];
            if (NetConnection.class.equals(clazz)) {
                args[i] = getConnection(channel);
            } else if (Request.class.equals(clazz)) {
                args[i] = request;
            } else if (NetMessage.class.isAssignableFrom(clazz)) {
                args[i] = request.getContent();
            } else {
                args[i] = null;
            }
        }
        Object obj = invoke.invoke(args);
        if (invoke.isVoid()) {
            return;
        }
        Response response;
        if (invoke.returnIsResponse()) {
            response = (Response) obj;
        } else {
            response = new Response();
            response.setStatusCode(200);
            response.setContent(obj);
        }
        response.setRequestId(request.getRequestId());
        connection.sendMessage(response);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        bindChannel(ctx.channel());
        super.channelActive(ctx);
    }

    private boolean bindChannel(Channel channel) {
        Attribute<NetConnection> netConnectionAttribute = channel.attr(netConnection);
        NetConnection netConnection = new NetConnection(channel);
        return netConnectionAttribute.compareAndSet(null, netConnection);
    }

    private NetConnection getConnection(Channel channel) {
        Attribute<NetConnection> attr = channel.attr(netConnection);
        return attr.get();
    }
}
