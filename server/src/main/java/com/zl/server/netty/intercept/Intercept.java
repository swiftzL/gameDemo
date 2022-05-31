package com.zl.server.netty.intercept;

import com.zl.server.netty.connection.NetConnection;
import com.zl.server.netty.model.Request;

import java.util.List;

public interface Intercept {
    Intercept loginIntercept = new LoginIntercept();

    boolean preHandle(NetConnection netConnection, Request request);
}
