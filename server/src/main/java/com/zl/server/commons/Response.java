package com.zl.server.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal();

    private int requestId;
    private int statusCode;
    private byte[] content;


    public static void setRequestId(Integer id){
        threadLocal.set(id);
    }

    public static Response err(String message) {
        return new Response(threadLocal.get(), 500, message.getBytes());
    }

    public static Response success(String message) {
        return new Response(threadLocal.get(), 200, message.getBytes());
    }


}
