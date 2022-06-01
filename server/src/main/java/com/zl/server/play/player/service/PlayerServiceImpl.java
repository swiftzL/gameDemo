package com.zl.server.play.player.service;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.play.bag.context.PropsContext;
import com.zl.server.play.bag.item.Item;
import com.zl.server.play.bag.model.Bag;
import com.zl.server.play.bag.model.BagBox;
import com.zl.server.play.bag.resource.Props;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.base.model.AttrStorage;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Storage
    private EntityCache<Integer, Account> accountEntityCache;
    @Storage
    private EntityCache<Integer, Bag> bagEntityCache;

    public void addLevel(Integer playerId, int num) {
        Account account = accountEntityCache.load(playerId);
        account.setLevel(account.getLevel() + num);
        accountEntityCache.writeBack(account);
    }

    public void addAttack(Integer playerId, int attack) {
        Account account = accountEntityCache.load(playerId);
        AttrStorage attrStorage = account.getModel().getAttrStorage();
        attrStorage.setAttack(attrStorage.getAttack() + attack);
        accountEntityCache.writeBack(account);
    }

    public int getLevel(Integer playerId) {
        Account account = accountEntityCache.load(playerId);
        return account.getLevel();
    }

    @Override
    public Account getAccount(Integer playerId) {
        Account account = accountEntityCache.load(playerId);
        return account;
    }
}
