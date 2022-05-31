package com.zl.server.play.player.service;

public interface PlayerService {
    void addLevel(Integer playerId, int num);

    void addAttack(Integer playerId, int attack);

    int getLevel(Integer playerId);

    boolean bagIsFull(Integer playerId);

    boolean verifyBag(Integer playerId, int propsId, int propsNum);

    boolean addProps(Integer playerId, int propsId, int propsNum);
}
