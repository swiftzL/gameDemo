package com.zl.server.play.bag.itemaction;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.play.bag.resource.ItemAction;
import com.zl.server.play.base.model.Account;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class ExperienceDrugAction implements ItemAction {
    @Storage
    private EntityCache<Integer, Account> entityCache;

    @Override
    public void action(int playerId, int num) {
        Account account = this.entityCache.load(playerId);
        account.setLevel(account.getLevel() + 1);
        this.entityCache.writeBack(account);
    }

    @Override
    public int getId() {
        return 1;
    }
}
