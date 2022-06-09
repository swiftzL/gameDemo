package com.zl.server.play.bag.item.action;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.netty.utils.NetMessageUtil;
import com.zl.server.play.bag.context.PropsContext;
import com.zl.server.play.bag.item.Item;
import com.zl.server.play.bag.item.ItemType;
import com.zl.server.play.bag.resource.param.ExperienceDrugParam;
import com.zl.server.play.base.event.UpgradeEvent;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.base.packet.MR_AccountInfo;
import com.zl.server.GameContext;
import com.zl.server.play.player.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ExperienceDrugAction implements ItemAction {
    @Storage
    private EntityCache<Integer, Account> entityCache;


    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void action(int modelId, Integer playerId, int num, Item item) {
        ExperienceDrugParam param = PropsContext.getItemParam(modelId, ExperienceDrugParam.class);
        handleExperience(playerId, num, param.getLeve());
    }

    private void handleExperience(Integer playerId, int num, int level) {
        int currentLevel = num * level;
        PlayerService playerService = GameContext.getPlayerService();
        playerService.addLevel(playerId, currentLevel);

        MR_AccountInfo mr_accountInfo = new MR_AccountInfo();
        mr_accountInfo.setLevel(currentLevel);

        applicationContext.publishEvent(UpgradeEvent.valueOf(playerId, this, currentLevel));
        NetMessageUtil.sendMessage(playerId, mr_accountInfo);
    }

    @Override
    public int getType() {
        return ItemType.Drug.getCode();
    }
}
