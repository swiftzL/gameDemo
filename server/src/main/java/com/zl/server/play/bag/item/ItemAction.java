package com.zl.server.play.bag.item;

public interface ItemAction {
    void action(int modelId, Integer playerId, int num);

    int getId();
}
