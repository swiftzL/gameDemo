package com.zl.server.netty.connection;

import com.zl.common.message.NetMessage;
import com.zl.server.netty.model.Response;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NetConnection {
    private volatile Channel channel;
    private Map<String, Object> attrs = new ConcurrentHashMap<>();
    private static Integer notificationId = -1;
    public static AttributeKey<NetConnection> netConnection = AttributeKey.valueOf("netConnection");

    public NetConnection(Channel channel) {
        this.channel = channel;
    }

    public void sendMessage(NetMessage message) {
        Response response = new Response();
        response.setContent(message);
        response.setRequestId(notificationId);
        this.channel.writeAndFlush(response);
    }

    public void sendMessage(Response response) {
        this.channel.writeAndFlush(response);
    }

    public <T> T getAttr(String key, Class<T> clazz) {
        return (T) attrs.get(key);
    }

    public boolean hashAttr(String key) {
        return attrs.containsKey(key);
    }

    public Object getAttr(String key) {
        return attrs.get(key);
    }

    public void setAttr(String key, Object value) {
        attrs.put(key, value);
    }
}
