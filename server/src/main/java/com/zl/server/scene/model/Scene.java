package com.zl.server.scene.model;

import com.zl.server.play.fight.model.Point;
import com.zl.server.scene.player.model.PlayerModel;

public interface Scene {

    void join(Integer playerId, PlayerModel playerModel);

    void setSceneId(Integer sceneId);

    int getSceneId();

    void quit(Integer playerId);

    boolean movePoint(Integer playerId, Point point);

    boolean canJoin();


}
