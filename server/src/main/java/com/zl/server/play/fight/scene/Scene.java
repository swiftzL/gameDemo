package com.zl.server.play.fight.scene;

import com.zl.server.netty.connection.NetConnection;

public interface Scene {

    void join(Integer playerId, NetConnection netConnection);
}
