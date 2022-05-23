package com.zl.server.netty;

import com.zl.common.message.NetMessage;
import com.zl.server.commons.Response;
import io.netty.channel.Channel;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NetConnection {
    private volatile Channel channel;
    private Map<String, Object> attrs = new ConcurrentHashMap<>();

    public NetConnection(Channel channel){
        this.channel = channel;
    }

    public void sendMessage(NetMessage message) {
        Response response = new Response();
        response.setContent(message);
        response.setRequestId(getAttr("requestId",Integer.class));
        this.channel.writeAndFlush(response);
    }

    public void sendMessage(Response response){
        this.channel.writeAndFlush(response);
    }

    public <T> T getAttr(String key, Class<T> clazz) {
        return (T) attrs.get(key);
    }

    public void setAttr(String key,Object value){
        attrs.put(key,value);
    }
}
