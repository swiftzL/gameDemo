package com.zl.server.netty.intercept;

import com.zl.server.commons.Command;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.netty.model.Request;
import com.zl.server.play.base.packet.MR_Response;

public class LoginIntercept implements Intercept {

    @Override
    public boolean preHandle(NetConnection netConnection, Request request) {
        if (request.getCommand() == Command.Login.getCode()||netConnection.hashAttr("id")) {
            return true;
        }
       netConnection.sendMessage(new MR_Response("当前用户没有登录"));
        return false;
    }
}
