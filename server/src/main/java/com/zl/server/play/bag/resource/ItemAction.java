package com.zl.server.play.bag.resource;

public interface ItemAction {
    void action(int playerId, int num);

    int getId();
}
