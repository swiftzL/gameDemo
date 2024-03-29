package com.zl.client.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private int requestId;
    private int command;
    private byte[] content;

    public static Response err(String message) {
        return new Response(1, 500, message.getBytes());
    }

}
