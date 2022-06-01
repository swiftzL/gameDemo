package com.zl.server.play.player.service;

import com.zl.server.play.base.model.Account;

public interface PlayerService {
    void addLevel(Integer playerId, int num);

    void addAttack(Integer playerId, int attack);

    int getLevel(Integer playerId);

    Account getAccount(Integer playerId);
}
