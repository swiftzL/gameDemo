package com.zl.server.netty.model;

import lombok.Data;

@Data
public class Request {

    private int requestId;
    private int command;
    private Object content;
}
