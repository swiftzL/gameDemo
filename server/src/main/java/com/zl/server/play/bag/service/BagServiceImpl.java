package com.zl.server.play.bag.service;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.netty.utils.NetMessageUtil;
import com.zl.server.play.bag.context.PropsContext;
import com.zl.server.play.bag.model.Bag;
import com.zl.server.play.bag.model.BagBox;
import com.zl.server.play.bag.packet.*;
import com.zl.server.play.bag.resource.Props;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.bag.item.Item;
import com.zl.server.GameContext;
import com.zl.server.play.player.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BagServiceImpl implements BagService {

    @Storage
    private EntityCache<Integer, Bag> bagEntityCache;

    @Storage
    private EntityCache<Integer, Account> accountEntityCache;

    @Autowired
    private PropsContext propsContext;

    @Override
    public void putProps(Integer playerId, MS_Props req) {
        if (req.getNum() <= 0) {
            NetMessageUtil.sendMessage(playerId, new MR_Response("非法参数"));
            return;
        }
        if (addProps(playerId, req.getPropsId(), req.getNum())) {
            NetMessageUtil.sendMessage(playerId, new MR_Response("添加成功"));
            return;
        }
        NetMessageUtil.sendMessage(playerId, new MR_Response("背包容量不够"));
    }

    //校验道具类型
    private boolean verifyType(int[] idxs, Item[] items, MS_ConsumeProps req) {
        int num = 0;
        for (int idx : idxs) {
            if (items[idx] == null) {
                continue;
            }
            if (items[idx].getModelId() != req.getModelId()) {
                return false;
            } else {
                num += items[idx].getCount();
            }
        }
        return num >= req.getNum();
    }

    @Override
    public void consumeProps(Integer playerId, MS_ConsumeProps req) {
        if (req.getNum() <= 0) {
            NetMessageUtil.sendMessage(playerId, new MR_Response("非法参数"));
            return;
        }
        int[] idxs = req.getIdxs();
        int num = req.getNum();
        Bag bag = bagEntityCache.loadOrCreate(playerId);
        int bagCap = bag.getModel().getBagCap();
        Item[] items = bag.getModel().getItems();

        if (!verifyType(idxs, items, req)) {
            NetMessageUtil.sendMessage(playerId, new MR_Response("校验失败"));
            return;
        }
        bagCap = delProps(idxs, items, num, bagCap);
        bag.getModel().setBagCap(bagCap);
        bagEntityCache.writeBack(bag);
        propsContext.action(req.getModelId(), playerId, req.getNum(), null);
        NetMessageUtil.sendMessage(playerId, new MR_Response("使用道具成功"));
    }

    private int delProps(int[] idxs, Item[] items, int num, int bagCap) {
        for (int idx : idxs) {
            if (items[idx] == null) {
                continue;
            }
            int count = items[idx].getCount();
            if (count <= num) {
                items[idx] = null;
                bagCap++;
                num -= count;
            } else {
                items[idx].setCount(count - num);
                break;
            }
        }
        return bagCap;
    }

    @Override
    public void showBag(Integer playerId) {
        Bag bag = bagEntityCache.loadOrCreate(playerId);
        BagBox model = bag.getModel();
        MR_BagStatus packet = new MR_BagStatus();
        packet.setBagCap(model.getBagCap());
        packet.setItems(model.getItems());
        NetMessageUtil.sendMessage(playerId, packet);
    }

    @Override
    public boolean bagIsFull(Integer playerId) {
        Bag bag = bagEntityCache.loadOrCreate(playerId);
        return bag.getModel().getBagCap() == 0;
    }

    @Override
    public boolean verifyBag(Integer playerId, int propsId, int propsNum) {
        Props props = PropsContext.getProps(propsId);
        Bag bag = bagEntityCache.loadOrCreate(playerId);
        BagBox model = bag.getModel();
        int num = propsNum;
        return model.getBagCap() >= (num / props.getCount()) + (num % props.getCount() == 0 ? 0 : 1);
    }

    //整理
    @Override
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
        int remainIndex = getRemainIndex(items, propsId, props.getCount());
        if (remainIndex != -1) {
            //将没填满的补齐
            num = addRemain(items[remainIndex], num, props.getCount());
        }
        //新增加的道具
        if (num > 0) {
            Item[] newItems = PropsContext.createItems(propsId, num);
            addBag(items, newItems);
            int bagCap = model.getBagCap();
            model.setBagCap(bagCap - newItems.length);
        }
        bagEntityCache.writeBack(bag);
        return true;
    }

    public int getRemainIndex(Item[] items, int id, int count) {
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            if (item != null && item.getModelId() == id && item.getCount() < count) {
                return i;
            }
        }
        return -1;
    }

    public void addBag(Item[] items, Item[] newItems) {
        int idx = 0;
        for (int i = 0; i < items.length && idx < newItems.length; i++) {
            Item item = items[i];
            if (item == null) {
                items[i] = newItems[idx++];
            }
        }
    }

    public int addRemain(Item item, int num, int count) {
        int remainSpace = count - item.getCount();
        item.setCount(Math.min(remainSpace, num) + item.getCount());
        num -= remainSpace;
        return num;
    }

}
