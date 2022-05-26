package com.zl.server.play.bag.context;

import com.zl.server.play.bag.model.Props;
import com.zl.server.play.bag.resource.ExperienceDrug;
import com.zl.server.play.bag.item.Item;
import com.zl.server.play.bag.item.ItemAction;
import com.zl.server.play.bag.item.ItemType;
import com.zl.server.play.bag.resource.ExperienceDrugParam;
import com.zl.server.play.bag.resource.ItemParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PropsContext {

    private static Map<Integer, Class<? extends Item>> itemMap = new HashMap<>();
    private static Map<Integer, Props> propsMap = new HashMap<>();
    private Map<Integer, ItemAction> itemActionMap = new HashMap<>();


    @Autowired
    public PropsContext(List<ItemAction> itemActionList) {
        for (ItemAction itemAction : itemActionList) {
            this.itemActionMap.put(itemAction.getId(), itemAction);
        }
    }

    public static Props getProps(int id) {
        return propsMap.get(id);
    }

    public static <T extends ItemParam> T getItemParam(int itemId, Class<T> tClass) {
        Props props = propsMap.get(itemId);
        return (T) props.getItemParam();
    }

    public static Item getItem(int id, int count) throws Exception {
        Class<? extends Item> aClass = itemMap.get(id);
        Item item = aClass.newInstance();
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
        props2.setId(1);
        props.setItemParam(new ExperienceDrugParam(1));


        propsMap.put(1, props);
        propsMap.put(1, props2);
        itemMap.put(1, ExperienceDrug.class);

    }

    public void action(int modelId, int playerId, int num) {
        this.itemActionMap.get(getProps(modelId).getType()).action(modelId, playerId, num);
    }


}
