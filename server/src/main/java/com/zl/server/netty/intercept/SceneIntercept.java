package com.zl.server.netty.intercept;

import com.zl.server.commons.Constants;
import com.zl.server.netty.anno.NetMessageInvoke;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.netty.model.Request;
import com.zl.server.play.base.packet.MR_Response;

public class SceneIntercept implements Intercept {
    @Override
    public boolean preHandle(NetConnection netConnection, Request request, NetMessageInvoke netMessageInvoke) {
        if (netMessageInvoke.commandType() == Constants.SCENE_COMMAND && netConnection.getSceneId() == null) {
            netConnection.sendMessage(new MR_Response("当前场景不存在"));
            return false;
        }
        if (netMessageInvoke.commandType() == Constants.NORMAL_COMMAND && netConnection.getSceneId() != null) {
            netConnection.sendMessage(new MR_Response("当前场景未退出"));
            return false;
        }
        return true;
    }
}
