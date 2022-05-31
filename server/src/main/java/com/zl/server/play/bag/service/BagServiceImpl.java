package com.zl.server.play.bag.service;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.netty.utils.NetMessageUtil;
import com.zl.server.play.bag.context.PropsContext;
import com.zl.server.play.bag.model.Bag;
import com.zl.server.play.bag.model.BagBox;
import com.zl.server.play.bag.packet.*;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.bag.item.Item;
import com.zl.server.play.player.PlayerServiceContext;
import com.zl.server.play.player.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BagServiceImpl implements BagService {

    @Storage
    private EntityCache<Integer, Bag> bagEntityCache;

    @Storage
    private EntityCache<Integer, Account> accountEntityCache;

    @Autowired
    private PlayerServiceContext playerContext;

    @Autowired
    private PropsContext propsContext;

    @Override
    public void putProps(Integer playerId, MS_Props req) {
        PlayerService playerService = PlayerServiceContext.getPlayerService();
        if (playerService.addProps(playerId, req.getPropsId(), req.getNum())) {
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

    public void consumeProps(Integer playerId, MS_ConsumeProps req) {
        int[] idxs = req.getIdxs();
        int num = req.getNum();
        Bag bag = bagEntityCache.loadOrCreate(playerId);
        int bagCap = bag.getModel().getBagCap();
        Item[] items = bag.getModel().getItems();

        if (!verifyType(idxs, items, req)) {
            NetMessageUtil.sendMessage(playerId, new MR_Response("校验失败"));
            return;
        }
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

        bag.getModel().setBagCap(bagCap);
        bagEntityCache.writeBack(bag);
        propsContext.action(req.getModelId(), playerId, req.getNum(), null);
        NetMessageUtil.sendMessage(playerId, new MR_Response("使用道具成功"));
    }

    public void showBag(Integer playerId) {
        Bag bag = bagEntityCache.loadOrCreate(playerId);
        BagBox model = bag.getModel();
        MR_BagStatus packet = new MR_BagStatus();
        packet.setBagCap(model.getBagCap());
        packet.setItems(model.getItems());
        NetMessageUtil.sendMessage(playerId, packet);
    }

}
