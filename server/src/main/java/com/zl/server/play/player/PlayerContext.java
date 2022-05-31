package com.zl.server.play.player;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.play.bag.context.PropsContext;
import com.zl.server.play.bag.item.Item;
import com.zl.server.play.bag.model.Bag;
import com.zl.server.play.bag.model.BagBox;
import com.zl.server.play.bag.packet.MS_Props;
import com.zl.server.play.bag.resource.Props;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.base.model.AttrStorage;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PlayerContext {

    public static PlayerContext INSTANCE;

    @Storage
    private EntityCache<Integer, Account> accountEntityCache;
    @Storage
    private EntityCache<Integer, Bag> bagEntityCache;

    @PostConstruct
    public void init() {
        PlayerContext.INSTANCE = this;
    }

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
        if (model.getBagCap() < (num / props.getCount()) + (num % props.getCount() == 0 ? 0 : 1)) {
            return false;
        }
        return true;
    }

    public boolean addProps(Integer playerId, int propsId, int propsNum) throws Exception {
        Props props = PropsContext.getProps(propsId);
        Bag bag = bagEntityCache.loadOrCreate(playerId);
        BagBox model = bag.getModel();
        int num = propsNum;
        if (model.getBagCap() < (num / props.getCount()) + (num % props.getCount() == 0 ? 0 : 1)) {
            return false;
        }

        Item[] items = model.getItems();
        for (int i = 0; i < items.length && num != 0; i++) {
            if (items[i] == null) {
                int count = num >= props.getCount() ? props.getCount() : num;
                items[i] = PropsContext.getItem(props.getId(), count);
                num -= count;
            }
        }
        bagEntityCache.writeBack(bag);
        return true;
    }

}
