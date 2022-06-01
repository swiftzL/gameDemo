package com.zl.server.play.bag.context;

import com.zl.server.play.bag.resource.AttackEquip;
import com.zl.server.play.bag.resource.param.AttackParam;
import com.zl.server.play.bag.resource.Props;
import com.zl.server.play.bag.resource.ExperienceDrug;
import com.zl.server.play.bag.item.Item;
import com.zl.server.play.bag.item.action.ItemAction;
import com.zl.server.play.bag.item.ItemType;
import com.zl.server.play.bag.resource.param.ExperienceDrugParam;
import com.zl.server.play.bag.resource.param.ItemParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 道具相关配置
 */
@Component
public class PropsContext {

    //道具配置表 id - item
    private static Map<Integer, Class<? extends Item>> itemMap = new HashMap<>();
    //道具参数配置表
    private static Map<Integer, Props> propsMap = new HashMap<>();
    //道具使用方法配置表
    private Map<Integer, ItemAction> itemActionMap = new HashMap<>();


    @Autowired
    public PropsContext(List<ItemAction> itemActionList) {
        for (ItemAction itemAction : itemActionList) {
            this.itemActionMap.put(itemAction.getType(), itemAction);
        }
    }

    public static Props getProps(int id) {
        return propsMap.get(id);
    }

    public static <T extends ItemParam> T getItemParam(Integer itemId, Class<T> tClass) {
        Props props = propsMap.get(itemId);
        return (T) props.getItemParam();
    }


    public static Item[] createItems(int id, int num) {
        Props props = getProps(id);
        int count = props.getCount();
        int itemNum = num / count + (num % count == 0 ? 0 : 1);
        int remain = num % count;
        Item[] items = new Item[itemNum];
        for (int i = 0; i < itemNum; i++) {
            items[i] = createItem(id, count);
        }
        if (remain != 0) {
            items[itemNum - 1].setCount(remain);
        }
        return items;
    }

    private static Item createItem(int id, int count) {
        Class<? extends Item> aClass = itemMap.get(id);
        Item item;
        try {
            item = aClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        item.setModelId(id);
        item.setCount(count);
        return item;
    }

    static {
        Props props = new Props();
        props.setCount(2);
        props.setName("大经验丹");
        props.setType(ItemType.Drug.getCode());
        props.setId(1);
        props.setItemParam(new ExperienceDrugParam(2));

        Props props2 = new Props();
        props2.setCount(2);
        props2.setName("小经验丹");
        props2.setType(ItemType.Drug.getCode());
        props2.setId(2);
        props2.setItemParam(new ExperienceDrugParam(1));

        Props props3 = new Props();
        props3.setType(ItemType.Equipment_ATTACK.getCode());
        props3.setName("短剑");
        props3.setCount(1);
        props3.setItemParam(new AttackParam(2));
        props3.setId(3);


        propsMap.put(1, props);
        propsMap.put(2, props2);
        propsMap.put(3, props3);
        itemMap.put(1, ExperienceDrug.class);
        itemMap.put(2, ExperienceDrug.class);
        itemMap.put(3, AttackEquip.class);

    }

    /**
     * 道具使用
     *
     * @param modelId
     * @param playerId
     * @param num
     */
    public void action(int modelId, int playerId, int num, Item item) {
        this.itemActionMap.get(getProps(modelId).getType()).action(modelId, playerId, num, item);
    }


}
