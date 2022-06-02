package com.zl.server.netty.handler;

import com.zl.common.message.NetMessage;
import com.zl.common.message.SceneNetMessage;
import com.zl.server.commons.Constants;
import com.zl.server.netty.anno.Param;
import com.zl.server.netty.intercept.Intercept;
import com.zl.server.netty.intercept.LoginIntercept;
import com.zl.server.netty.model.Request;
import com.zl.server.netty.model.Response;
import com.zl.server.netty.config.NetMessageProcessor;
import com.zl.server.netty.invoke.Invoke;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.netty.threadpool.DispatchExecutor;
import com.zl.server.netty.threadpool.Task;
import com.zl.server.netty.threadpool.TaskExecutor;
import com.zl.server.play.base.packet.MR_Response;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Parameter;
import java.util.Map;

@Slf4j
public class ServiceHandler extends ChannelInboundHandlerAdapter {


    private static TaskExecutor playerExecutor = DispatchExecutor.getExecutor(4, Constants.PlyerThreadName);
    private static TaskExecutor sceneExecutor = DispatchExecutor.getExecutor(4, Constants.SceneThreadName);
    private static Map<Integer, Invoke> invokes = NetMessageProcessor.invokes;
    private static Intercept intercept = Intercept.loginIntercept;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        NetConnection netConnection = getConnection(ctx.channel());
        Request request = (Request) msg;
        Invoke invoke = invokes.get(request.getCommand());
        if (invoke == null) {
            netConnection.sendMessage(new MR_Response("指令错误"));
            return;
        }

        //拦截
        if (!intercept.preHandle(netConnection, request)) {
            return;
        }
        Integer id = getTaskId(request.getContent(), netConnection);
        if (id == null) {
            exec(netConnection, invoke, request);
        } else {
            TaskExecutor taskExecutor = selectExecutor(request.getContent());
            taskExecutor.execute(new Task(id, () -> {
                try {
                    exec(netConnection, invoke, request);
                } catch (Exception exception) {
                    log.error("exec exception " + exception);
                }
            }));
        }
    }

    private TaskExecutor selectExecutor(Object object) {
        if (object.getClass().isAssignableFrom(SceneNetMessage.class)) {
            return sceneExecutor;
        }
        return playerExecutor;
    }

    private Integer getTaskId(Object object, NetConnection netConnection) {
        if (object.getClass().isAssignableFrom(SceneNetMessage.class)) {
            return netConnection.getSceneId();
        }
        return netConnection.getPlayerId();
    }

    //执行业务
    private void exec(NetConnection netConnection, Invoke invoke, Request msg) {
        Object[] args = parseArgs(invoke, netConnection, msg);
        Object obj = null;
        try {
            obj = invoke.invoke(args);
        } catch (Exception e) {
            netConnection.sendMessage(new MR_Response(e.toString()));
        }
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
        response.setRequestId(msg.getRequestId());
        netConnection.sendMessage(response);
    }

    private Object[] parseArgs(Invoke invoke, NetConnection netConnection, Request request) {
        Class[] argsType = invoke.getArgsType();
        Object[] args = new Object[argsType.length];
        Parameter[] parameters = invoke.getParameters();
        for (int i = 0; i < args.length; i++) {
            Class clazz = argsType[i];
            Parameter parameter = parameters[i];
            if (NetConnection.class.equals(clazz)) {
                args[i] = netConnection;
            } else if (Request.class.equals(clazz)) {
                args[i] = request;
            } else if (NetMessage.class.isAssignableFrom(clazz)) {
                args[i] = request.getContent();
            } else if (parameter.getAnnotation(Param.class) != null) {
                Param param = parameter.getAnnotation(Param.class);
                args[i] = netConnection.getAttr(param.value());
            } else {
                args[i] = null;
            }
        }
        return args;
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
