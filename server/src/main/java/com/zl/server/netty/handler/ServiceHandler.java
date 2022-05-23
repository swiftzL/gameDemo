package com.zl.server.netty.handler;

import com.zl.common.message.NetMessage;
import com.zl.server.netty.model.Request;
import com.zl.server.netty.model.Response;
import com.zl.server.netty.config.NetMessageProcessor;
import com.zl.server.netty.dispatch.Invoke;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.netty.threadpool.DispatchExecutor;
import com.zl.server.netty.threadpool.Executor;
import com.zl.server.netty.threadpool.Task;
import com.zl.server.play.base.packet.MR_Response;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Slf4j
public class ServiceHandler extends ChannelInboundHandlerAdapter {

    private Map<Integer, Invoke> invokes = NetMessageProcessor.invokes;
    private Executor executor = DispatchExecutor.ExecutorHolder.INSTANCE;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        NetConnection netConnection = getConnection(ctx.channel());
        Integer id = netConnection.getAttr("id", Integer.class);
        if (id == null) {
            exec(netConnection, msg);
        } else {
            executor.execute(new Task(id, () -> {
                try {
                    exec(netConnection, msg);
                } catch (Exception exception) {
                    log.error("exec exception " + exception);
                }
            }));
        }
    }

    private void exec(NetConnection netConnection, Object msg) throws
            InvocationTargetException, IllegalAccessException {
        Request request = (Request) msg;
        Invoke invoke = invokes.get(request.getCommand());
        if (invoke == null) {
            netConnection.sendMessage(new MR_Response("指令错误"));
            return;
        }
        Class[] argsType = invoke.getArgsType();
        Object[] args = new Object[argsType.length];
        for (int i = 0; i < args.length; i++) {
            Class clazz = argsType[i];
            if (NetConnection.class.equals(clazz)) {
                args[i] = netConnection;
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
        netConnection.sendMessage(response);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        bindChannel(ctx.channel());
        super.channelActive(ctx);
    }

    private boolean bindChannel(Channel channel) {
        Attribute<NetConnection> netConnectionAttribute = channel.attr(NetConnection.netConnection);
        NetConnection netConnection = new NetConnection(channel);
        return netConnectionAttribute.compareAndSet(null, netConnection);
    }

    private NetConnection getConnection(Channel channel) {
        Attribute<NetConnection> attr = channel.attr(NetConnection.netConnection);
        return attr.get();
    }
}
