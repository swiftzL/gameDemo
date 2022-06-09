package com.zl.server.netty.intercept;

import com.zl.server.netty.anno.NetMessageInvoke;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.netty.model.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Intercept {
    List<Intercept> interceptList = Arrays.asList(new LoginIntercept(), new SceneIntercept());

    boolean preHandle(NetConnection netConnection, Request request, NetMessageInvoke netMessageInvoke);
}
