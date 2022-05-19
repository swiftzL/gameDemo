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
    private String message;
    private byte[] content;

    public static Response err(String message) {
        return new Response(1, 500, message, null);
    }

}
