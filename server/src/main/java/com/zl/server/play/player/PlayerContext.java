package com.zl.server.play.player;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.base.model.AttrModel;
import org.springframework.stereotype.Component;

@Component
public class PlayerContext {

    @Storage
    private EntityCache<Integer, Account> accountEntityCache;

    public void addLevel(Integer playerId, int num) {
        Account account = accountEntityCache.load(playerId);
        account.setLevel(account.getLevel() + num);
        accountEntityCache.writeBack(account);
    }

    public void addAttack(Integer playerId, int attack) {
        Account account = accountEntityCache.load(playerId);
        AttrModel attrModel = account.getModel().getAttrModel();
        attrModel.setAttack(attrModel.getAttack() + attack);
        accountEntityCache.writeBack(account);
    }

}
