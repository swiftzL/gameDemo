package com.zl.server.netty.connection;

import com.zl.common.message.NetMessage;
import com.zl.server.netty.model.Response;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NetConnection {
    private volatile Channel channel;
    private Map<String, Object> attrs;
    private static Integer notificationId = -1;
    public static AttributeKey<NetConnection> netConnection = AttributeKey.valueOf("netConnection");
    private static String SCENE_ID = "scene_id";

    public NetConnection(Channel channel) {
        this.channel = channel;
        init();
    }

    public void init() {
        this.attrs = new ConcurrentHashMap<>();
    }

    public void sendMessage(NetMessage message) {
        Response response = new Response();
        response.setContent(message);
        response.setRequestId(notificationId);
        this.channel.writeAndFlush(response);
    }

    public Integer getPlayerId() {
        return this.getAttr("id", Integer.class);
    }

    public Integer getSceneId() {
        return this.getAttr(SCENE_ID, Integer.class);
    }

    public void setSceneId(Integer sceneId) {
        this.setAttr(SCENE_ID, sceneId);
    }

    public void removeScene() {
        this.attrs.remove(SCENE_ID);
    }

    public void close(){
        this.channel.close();
    }

    public void removePlayer() {
        this.attrs.remove("id");
    }

    public void sendMessage(Response response) {
        this.channel.writeAndFlush(response);
    }

    public <T> T getAttr(String key, Class<T> clazz) {
        Object o = attrs.get(key);
        if (o == null) {
            return null;
        }
        return (T) o;
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

    public Map<String, Object> getAttrs() {
        return attrs;
    }

    public void copy(NetConnection netConnection) {
        this.attrs = netConnection.getAttrs();
    }
}
