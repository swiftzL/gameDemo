package com.zl.server.play.fight.scene;

import com.zl.server.GameContext;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.base.model.AccountBox;
import com.zl.server.play.base.model.AttrStorage;
import com.zl.server.play.fight.player.PlayerManager;
import com.zl.server.play.fight.player.PlayerMapManager;
import com.zl.server.play.fight.player.PlayerModel;
import com.zl.server.play.player.service.PlayerService;
import lombok.Getter;

import java.awt.*;

@Getter
public abstract class AbstractScene implements Scene {
    private Integer sceneId;
    private PlayerManager playerManager;
    private PlayerMapManager playerMapManager;

    public void join(Integer playerId, NetConnection netConnection) {
        PlayerService playerService = GameContext.getPlayerService();
        Account account = playerService.getAccount(playerId);
        if (account != null && !playerManager.exist(playerId)) {
            netConnection.setSceneId(this.getSceneId());
            PlayerModel playerModel = new PlayerModel();
            AccountBox model = account.getModel();
            AttrStorage attrStorage = model.getAttrStorage();
            playerModel.setPlayerId(playerId);
            playerModel.setAttack(attrStorage.getAttack());
            playerModel.setDefense(attrStorage.getDefense());
            playerModel.setHp(attrStorage.getHp());
            playerModel.setPoint(new Point(0, 0));
            this.playerManager.addPlayer(playerModel);
        }
    }

    public void exit(Integer playerId) {
        playerManager.delPlayer(playerId);
    }


}
