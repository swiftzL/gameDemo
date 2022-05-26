package com.zl.server.play.bag.itemaction;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.play.bag.resource.ItemAction;
import com.zl.server.play.base.model.Account;
import org.springframework.stereotype.Component;

@Component
public class DrugAction implements ItemAction {
    @Storage
    private EntityCache<Integer, Account> entityCache;

    @Override
    public void action(int modelId, Integer playerId, int num) {
        switch (modelId) {
            case 1:
                handleExperience(playerId, num);
                break;
        }

    }

    private void handleExperience(Integer playerId, int num) {
        Account account = this.entityCache.load(playerId);
        account.setLevel(account.getLevel() + 1);
        this.entityCache.writeBack(account);
    }


    @Override
    public int getId() {
        return 1;
    }
}
