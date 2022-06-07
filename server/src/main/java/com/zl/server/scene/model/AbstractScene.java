package com.zl.server.scene.model;

import com.zl.server.netty.utils.NetMessageUtil;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.scene.player.PlayerManager;
import com.zl.server.scene.player.PlayerMapManager;
import com.zl.server.scene.player.model.PlayerModel;
import lombok.Getter;
import lombok.Setter;


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
