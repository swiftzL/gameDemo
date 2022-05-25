package com.zl.server.play.bag.service;

import com.zl.common.message.NetMessage;
import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.play.bag.context.PropsContext;
import com.zl.server.play.bag.model.Bag;
import com.zl.server.play.bag.model.BagModel;
import com.zl.server.play.bag.model.Props;
import com.zl.server.play.bag.packet.MS_ConsumProps;
import com.zl.server.play.bag.packet.MS_Props;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.bag.resource.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BagServiceImpl implements BagService {

    @Storage
    private EntityCache<Integer, Bag> bagEntityCache;

    @Autowired
    private PropsContext propsContext;

    @Override
    public NetMessage putProps(int playerId, MS_Props ms_props) throws Exception {
        Props props = PropsContext.getProps(ms_props.getPropsId());
        Bag bag = bagEntityCache.loadOrCreate(playerId);
        BagModel model = bag.getModel();
        int num = ms_props.getNum();
        if (model.getBagCap() < (num / props.getCount()) + num % props.getCount()) {
            return new MR_Response("背包容量不够");
        }
        Item[] items = model.getItems();
        for (int i = 0; i < items.length && num != 0; i++) {
            if (items[i] == null) {
                int count = num >= props.getCount() ? props.getCount() : num;
                items[i] = PropsContext.getItem(props.getId(), count);
                num -= count;
            }
        }
        return new MR_Response("添加成功");
    }

    public NetMessage consumeProps(int playerId, MS_ConsumProps ms_consumProps) {
        int[] idxs = ms_consumProps.getIdxs();
        int num = ms_consumProps.getNum();
        Bag bag = bagEntityCache.loadOrCreate(playerId);
        int bagCap = bag.getModel().getBagCap();
        Item[] items = bag.getModel().getItems();
        for (int idx : idxs) {
            if (items[idx].getModelId() != ms_consumProps.getModelId()) {
                return new MR_Response("道具类型不一致");
            }
        }
        for (int idx : idxs) {
            int count = items[idx].getCount();
            if (count <= num) {
                items[idx] = null;
                bagCap--;
                num -= count;
            } else {
                items[idx].setCount(count - num);
                break;
            }
        }
        bag.getModel().setBagCap(bagCap);
        bagEntityCache.writeBack(bag);
        propsContext.action(ms_consumProps.getModelId(), playerId, ms_consumProps.getNum());
        return new MR_Response("使用道具成功");
    }

}
