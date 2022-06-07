package com.zl.server.play.fight.service;

import com.zl.server.netty.connection.NetConnection;
import com.zl.server.play.fight.packet.MS_Attack;
import com.zl.server.play.fight.packet.MS_CreateFightScene;
import com.zl.server.play.fight.packet.MS_JoinFightScene;
import com.zl.server.play.fight.packet.MS_MovePoint;

public interface FightSceneService {

    void createFightScene(Integer playerId, NetConnection netConnection, MS_CreateFightScene req);

    void joinFightScene(Integer playerId, NetConnection netConnection, MS_JoinFightScene req);

    void quitScene(Integer playerId, NetConnection netConnection);

    void movePoint(Integer playerId, NetConnection netConnection, MS_MovePoint req);

    void attack(Integer playerId, NetConnection netConnection, MS_Attack req);
}
