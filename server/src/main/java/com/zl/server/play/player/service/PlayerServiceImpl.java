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

    public boolean bagIsFull(Integer playerId) {
        Bag bag = bagEntityCache.loadOrCreate(playerId);
        return bag.getModel().getBagCap() == 0;
    }

    public boolean verifyBag(Integer playerId, int propsId, int propsNum) {
        Props props = PropsContext.getProps(propsId);
        Bag bag = bagEntityCache.loadOrCreate(playerId);
        BagBox model = bag.getModel();
        int num = propsNum;
        return model.getBagCap() >= (num / props.getCount()) + (num % props.getCount() == 0 ? 0 : 1);
    }

    //整理
    public boolean addProps(Integer playerId, int propsId, int propsNum) {
        //添加背包前先校验
        if (!verifyBag(playerId, propsId, propsNum)) {
            return false;
        }
        Props props = PropsContext.getProps(propsId);
        Bag bag = bagEntityCache.loadOrCreate(playerId);
        BagBox model = bag.getModel();
        int num = propsNum;

        Item[] items = model.getItems();
        for (int i = 0; i < items.length && num > 0; i++) {
            Item item = items[i];
            if (item == null) {
                int count = Math.min(num, props.getCount());
                items[i] = PropsContext.createItem(props.getId(), count);
                num -= count;
            } else if (item.getModelId() == propsId && item.getCount() < props.getCount()) {
                int remainSpace = props.getCount() - items[i].getCount();
                item.setCount(Math.min(remainSpace, num) + item.getCount());
                num -= remainSpace;
            }
        }
        bagEntityCache.writeBack(bag);
        return true;
    }
}
