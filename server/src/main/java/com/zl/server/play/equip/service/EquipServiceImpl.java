package com.zl.server.play.equip.service;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.netty.utils.NetMessageUtil;
import com.zl.server.play.bag.context.PropsContext;
import com.zl.server.play.bag.item.Item;
import com.zl.server.play.bag.model.Bag;
import com.zl.server.play.bag.model.BagBox;
import com.zl.server.play.bag.resource.Props;
import com.zl.server.play.bag.service.BagService;
import com.zl.server.play.equip.packet.MR_RemoveEquipment;
import com.zl.server.play.equip.packet.MR_UseEquipment;
import com.zl.server.play.equip.packet.MS_Equipment;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.GameContext;
import com.zl.server.play.player.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipServiceImpl implements EquipService {

    @Storage
    private EntityCache<Integer, Bag> bagEntityCache;

    @Autowired
    private GameContext playerContext;

    @Autowired
    private PropsContext propsContext;


    //从背包中取出道具或装备
    private Item getEquip(BagBox bagBox, Item[] items, int modelId) {
        Item equipment = null;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].getModelId() == modelId) {
                Item item = items[i];
                item.setCount(item.getCount() - 1);
                if (item.getCount() == 0) {
                    items[i] = null;
                    bagBox.setBagCap(bagBox.getBagCap() + 1);
                }
                equipment = item;
                break;
            }
        }
        return equipment;
    }

    //使用装备
    public void useEquipment(Integer playerId, MS_Equipment req) {
        Bag bag = bagEntityCache.loadOrCreate(playerId);
        Item[] items = bag.getModel().getItems();
        Item equipment = getEquip(bag.getModel(), items, req.getModelId());
        if (equipment == null) {
            NetMessageUtil.sendMessage(playerId, new MR_Response("装备不存在"));
            return;
        }
        bagEntityCache.writeBack(bag);
        propsContext.action(equipment.getModelId(), playerId, 1, equipment);
        MR_UseEquipment packet = new MR_UseEquipment();
        packet.setModelId(req.getModelId());
        NetMessageUtil.sendMessage(playerId, packet);
    }

    //移除装备
    @Override
    public void unUseEquipment(Integer playerId, MS_Equipment req) {
        Props props = PropsContext.getProps(req.getModelId());
        if (props == null) {
            NetMessageUtil.sendMessage(playerId, new MR_Response("装备不存在"));
            return;
        }
        if (removeEquipment(playerId, req.getModelId())) {
            MR_RemoveEquipment mr_removeEquipment = new MR_RemoveEquipment();
            mr_removeEquipment.setModelId(req.getModelId());
            NetMessageUtil.sendMessage(playerId, mr_removeEquipment);
            return;
        }
        NetMessageUtil.sendMessage(playerId, new MR_Response("装备栏移除失败"));
    }

    //移除装备 id
    private boolean removeEquipment(Integer playerId, int modelId) {
        BagService bagService = GameContext.getBagService();
        if (!bagService.addProps(playerId, modelId, 1)) {
            return false;
        }
        propsContext.action(modelId, playerId, -1, null);
        return true;
    }
}
