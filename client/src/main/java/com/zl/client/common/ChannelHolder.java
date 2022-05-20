package com.zl.client.common;


import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;


public class ChannelHolder {
    private static Map<Integer, CompletableFuture> responseMap = new ConcurrentHashMap<>();
    public static CompletableFuture getFuture(Integer requestId) {
        return responseMap.get(requestId);
    }
    public static void completable(Integer requestId, Object o) {
        //remove the requestId and return future
        CompletableFuture completableFuture = responseMap.remove(requestId);
        if (completableFuture != null) {
            completableFuture.complete(o);
            remove(requestId);
        } else {
            throw new IllegalArgumentException(requestId + "is not found");
        }
    }

    public static void remove(Integer requestId) {
        responseMap.remove(requestId);
    }

    public static void put(Integer requestId, CompletableFuture completableFuture) {
        responseMap.put(requestId, completableFuture);
    }


}
