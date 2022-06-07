package com.zl.server.play.fight.facade;

import com.zl.server.commons.Command;
import com.zl.server.commons.Constants;
import com.zl.server.netty.anno.NetMessageHandler;
import com.zl.server.netty.anno.NetMessageInvoke;
import com.zl.server.netty.anno.Param;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.fight.packet.MS_Attack;
import com.zl.server.play.fight.packet.MS_CreateFightScene;
import com.zl.server.play.fight.packet.MS_JoinFightScene;
import com.zl.server.play.fight.packet.MS_MovePoint;
import com.zl.server.play.fight.service.FightSceneService;
import com.zl.server.scene.Scene;
import com.zl.server.scene.SceneManager;
import org.springframework.beans.factory.annotation.Autowired;

@NetMessageHandler
public class FightSceneFacade {

    @Autowired
    private FightSceneService fightSceneService;

    @NetMessageInvoke(value = Command.CreateFightScene)
    public void createFightScene(@Param("id") Integer playerId, NetConnection netConnection, MS_CreateFightScene req) {
        this.fightSceneService.createFightScene(playerId, netConnection, req);
    }

    @NetMessageInvoke(value = Command.JoinScene)
    public void joinScene(@Param("id") Integer playerId, NetConnection netConnection, MS_JoinFightScene req) {
        this.fightSceneService.joinFightScene(playerId, netConnection, req);
    }

    @NetMessageInvoke(value = Command.QuitScene, commandType = Constants.SCENE_COMMAND)
    public void quitScene(@Param("id") Integer playerId, NetConnection netConnection) {
        this.fightSceneService.quitScene(playerId, netConnection);
    }

    @NetMessageInvoke(value = Command.MovePoint, commandType = Constants.SCENE_COMMAND)
    public void movePoint(@Param("id") Integer playerId, NetConnection netConnection, MS_MovePoint req) {
        this.fightSceneService.movePoint(playerId, netConnection, req);
    }

    @NetMessageInvoke(value = Command.Attack, commandType = Constants.SCENE_COMMAND)
    public void attack(@Param("id") Integer playerId, NetConnection netConnection, MS_Attack req) {
        this.fightSceneService.attack(playerId, netConnection, req);
    }


}
