package com.zl.server.scene.model;

import com.zl.server.netty.utils.NetMessageUtil;
import com.zl.server.play.fight.model.Point;
import com.zl.server.scene.operate.FightOperate;
import com.zl.server.scene.operate.SimpleFightOperate;
import com.zl.server.scene.player.PlayerManager;
import com.zl.server.scene.player.PlayerMapManager;
import com.zl.server.scene.player.model.PlayerModel;


public class FightScene extends AbstractScene {
    private FightOperate fightOperate;

    public FightScene(int down, int right, int maxCount) {
        this.fightOperate = new SimpleFightOperate();
        this.playerManager = new PlayerManager(maxCount);
        this.playerMapManager = new PlayerMapManager(down, right);
    }


    public void pk(Integer attackId, Integer attackedId) {
        PlayerManager playerManager = this.getPlayerManager();
        PlayerModel attackPlayer = playerManager.getPlayerModel(attackId);
        PlayerModel attackedPlayer = playerManager.getPlayerModel(attackedId);
        if (attackedPlayer == null) {
            NetMessageUtil.sendMessage(attackPlayer.getPlayerId(), "当前玩家不存在");
            return;
        }
        if (!attackPlayer.canAttack(attackedPlayer.getPoint())) {
            NetMessageUtil.sendMessage(attackPlayer.getPlayerId(), "距离不够 无法攻击");
            return;
        }
        fightOperate.pk(attackPlayer, attackedPlayer);
    }


    public boolean movePoint(Integer playerId, Point point) {
        PlayerManager playerManager = this.getPlayerManager();
        PlayerModel playerModel = playerManager.getPlayerModel(playerId);
        return this.getPlayerMapManager().move(playerModel, point);
    }


}
