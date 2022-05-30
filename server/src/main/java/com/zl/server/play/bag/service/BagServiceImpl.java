package com.zl.server.play.bag.service;

import com.zl.common.message.NetMessage;
import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.play.bag.context.PropsContext;
import com.zl.server.play.bag.model.Bag;
import com.zl.server.play.bag.model.BagModel;
import com.zl.server.play.bag.model.Props;
import com.zl.server.play.bag.packet.*;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.base.model.EquipmentModel;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.bag.item.Item;
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
    public NetMessage putProps(Integer playerId, MS_Props ms_props) throws Exception {
        if (addProps(playerId, ms_props)) {
            return new MR_Response("添加成功");
        }
        return new MR_Response("背包容量不够");
    }

    //添加道具
    private boolean addProps(Integer playerId, MS_Props ms_props) throws Exception {
        Props props = PropsContext.getProps(ms_props.getPropsId());
        Bag bag = bagEntityCache.loadOrCreate(playerId);
        BagModel model = bag.getModel();
        int num = ms_props.getNum();
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


    private boolean verifyType(int[] idxs, Item[] items, MS_ConsumeProps ms_consumProps) {
        int num = 0;
        for (int idx : idxs) {
            if (items[idx] == null) {
                continue;
            }
            if (items[idx].getModelId() != ms_consumProps.getModelId()) {
                return false;
            } else {
                num += items[idx].getCount();
            }
        }
        return num >= ms_consumProps.getNum();
    }

    public NetMessage consumeProps(Integer playerId, MS_ConsumeProps ms_consumeProps) {
        int[] idxs = ms_consumeProps.getIdxs();
        int num = ms_consumeProps.getNum();
        Bag bag = bagEntityCache.loadOrCreate(playerId);
        int bagCap = bag.getModel().getBagCap();
        Item[] items = bag.getModel().getItems();
        if (!verifyType(idxs, items, ms_consumeProps)) {
            return new MR_Response("失败");
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
        propsContext.action(ms_consumeProps.getModelId(), playerId, ms_consumeProps.getNum(), null);
        return new MR_Response("使用道具成功");
    }

    public MR_BagStatus showBag(Integer playerId) {
        Bag bag = bagEntityCache.loadOrCreate(playerId);
        BagModel model = bag.getModel();
        MR_BagStatus mr_bagStatus = new MR_BagStatus();
        mr_bagStatus.setBagCap(model.getBagCap());
        mr_bagStatus.setItems(model.getItems());
        return mr_bagStatus;
    }

    //使用装备
    public NetMessage useEquipment(Integer playerId, MS_Equipment ms_equipment) {
        Bag bag = bagEntityCache.loadOrCreate(playerId);
        Item equipment = null;
        Item[] items = bag.getModel().getItems();
        for (int i = 0; i < items.length; i++) {
            if (items[i].getModelId() == ms_equipment.getModelId()) {
                Item item = items[i];
                item.setCount(item.getCount() - 1);
                if (item.getCount() == 0) {
                    items[i] = null;
                }
                equipment = item;
                break;
            }
        }
        if (equipment == null) {
            throw new RuntimeException("当前装备不存在");
        }
        propsContext.action(equipment.getModelId(), playerId, 1, equipment);
        MR_UseEquipment mr_useEquipment = new MR_UseEquipment();
        mr_useEquipment.setModelId(ms_equipment.getModelId());
        return mr_useEquipment;
    }

    //移除装备
    public NetMessage removeEquipment(Integer playerId, MS_Equipment ms_equipment) throws Exception {
        if (removeEquipment(playerId, ms_equipment.getModelId())) {
            MR_RemoveEquipment mr_removeEquipment = new MR_RemoveEquipment();
            mr_removeEquipment.setModelId(ms_equipment.getModelId());
            return mr_removeEquipment;
        }
        return new MR_Response("装备栏移除失败");
    }

    private boolean removeEquipment(Integer playerId, int modelId) throws Exception {
        if (!addProps(playerId, new MS_Props(modelId, 1))) {
            return false;
        }
        propsContext.action(modelId, playerId, -1, null);
        return true;
    }
}
