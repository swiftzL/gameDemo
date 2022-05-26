package com.zl.server.play.bag.item.itemaction;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.play.bag.context.PropsContext;
import com.zl.server.play.bag.item.ItemAction;
import com.zl.server.play.bag.model.Props;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.player.PlayerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExperienceDrugAction implements ItemAction {
    @Storage
    private EntityCache<Integer, Account> entityCache;

    @Autowired
    private PlayerContext playerContext;

    @Override
    public void action(int modelId, Integer playerId, int num) {
        Props<Integer> props = PropsContext.getProps(modelId);
        handleExperience(playerId, num, props.getProperties());
    }

    private void handleExperience(Integer playerId, int num, int level) {
        playerContext.addLevel(playerId, num * level);
    }

    @Override
    public int getId() {
        return 1;
    }
}
