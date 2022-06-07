package com.zl.server.scene;

import com.zl.server.GameContext;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.netty.utils.NetMessageUtil;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.base.model.AccountBox;
import com.zl.server.play.base.model.AttrStorage;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.fight.model.Point;
import com.zl.server.scene.manager.PlayerManager;
import com.zl.server.scene.manager.PlayerMapManager;
import com.zl.server.scene.model.PlayerModel;
import com.zl.server.play.player.service.PlayerService;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.CompletableFuture;


@Getter
@Setter
public abstract class AbstractScene implements Scene {
    private int sceneId;
    private long deadline;
    protected PlayerManager playerManager;
    protected PlayerMapManager playerMapManager;


    public void join(Integer playerId, PlayerModel playerModel) {
        if (!canJoin()) {
            NetMessageUtil.sendMessage(playerId, new MR_Response("当前场景已满无法加入"));
            return;
        }
        this.playerManager.addPlayer(playerModel);
    }

    @Override
    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    public void quit(Integer playerId) {
        playerManager.delPlayer(playerId);
    }

    public boolean canJoin() {
        return this.playerManager.canJoin();
    }


}
