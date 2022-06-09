package com.zl.server.netty.utils;

import com.zl.common.message.NetMessage;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.play.base.packet.MR_Response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息发送工具类
 */
public class NetMessageUtil {

    private static Map<Integer, NetConnection> netConnectionMap = new ConcurrentHashMap<>();

    public static void sendMessage(Integer playerId, NetMessage netMessage) {
        netConnectionMap.get(playerId).sendMessage(netMessage);
    }

    public static void addConnection(Integer playerId, NetConnection netConnection) {
        NetConnection netConnectionOld = netConnectionMap.get(playerId);
        if (netConnectionOld != null) {
            netConnection.copy(netConnectionOld);
            netConnectionOld.close();
        }
        netConnectionMap.put(playerId, netConnection);
    }

    public static void removeConnection(Integer playerId) {
        netConnectionMap.remove(playerId);
    }

    public static void sendMessage(Integer playerId, String message) {
        sendMessage(playerId, new MR_Response(message));
    }

}
