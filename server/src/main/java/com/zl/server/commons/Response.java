package com.zl.server.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private int requestId;
    private int statusCode;
    private Object content;


    public static Response err(String message) {
        return new Response(0,500, message.getBytes());
    }

    public static Response success(String message) {
        return new Response(0, 200, message.getBytes());
    }


}
