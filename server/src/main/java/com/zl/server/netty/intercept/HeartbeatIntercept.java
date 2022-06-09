package com.zl.server.netty.intercept;

import com.zl.common.common.Command;
import com.zl.server.netty.anno.NetMessageInvoke;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.netty.model.Request;
import com.zl.server.netty.model.Response;

public class HeartbeatIntercept implements Intercept {
    @Override
    public boolean preHandle(NetConnection netConnection, Request request, NetMessageInvoke netMessageInvoke) {
        if (request.getCommand() == Command.Heartbeat.getCode()) {
            return false;
        }
        return true;
    }
}
