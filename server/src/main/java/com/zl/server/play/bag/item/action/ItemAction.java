package com.zl.server.play.bag.item.action;

import com.zl.server.play.bag.item.Item;

public interface ItemAction {
    void action(int modelId, Integer playerId, int num, Item item);
    int getType();
}
