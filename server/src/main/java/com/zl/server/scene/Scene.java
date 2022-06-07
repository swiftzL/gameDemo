package com.zl.server.scene;

import com.zl.server.netty.connection.NetConnection;
import com.zl.server.play.fight.model.Point;
import com.zl.server.scene.model.PlayerModel;

public interface Scene {

    void join(Integer playerId, PlayerModel playerModel);

    void setSceneId(Integer sceneId);

    int getSceneId();

    void quit(Integer playerId);

    boolean movePoint(Integer playerId, Point point);

    boolean canJoin();


}
