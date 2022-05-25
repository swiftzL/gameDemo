package com.zl.server.play.bag.service;

import com.zl.common.message.NetMessage;
import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.netty.anno.Param;
import com.zl.server.play.bag.model.Bag;
import com.zl.server.play.bag.model.BagModel;
import com.zl.server.play.bag.packet.MS_ConsumProps;
import com.zl.server.play.bag.packet.MS_Props;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.props.model.Props;
import com.zl.server.resource.Item;
import org.springframework.stereotype.Service;

@Service
public class BagServiceImpl implements BagService {

    @Storage
    private EntityCache<Integer, Bag> bagEntityCache;
    @Storage
    private EntityCache<Integer, Props> propsEntityCache;

    @Override
    public NetMessage putProps(int playerId, MS_Props ms_props) {
        Props props = propsEntityCache.loadOrCreate(ms_props.getPropsId());
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
                items[i] = new Item(props.getId(), count);
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
            if (items[idx].getType() != ms_consumProps.getType()) {
                return new MR_Response("道具类型不一致");
            }
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
        return new MR_Response("使用道具成功");
    }

}
