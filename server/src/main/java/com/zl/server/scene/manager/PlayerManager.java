package com.zl.server.scene.manager;


import com.zl.server.scene.model.PlayerModel;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private int current;
    private int maxCount;

    private Map<Integer, PlayerModel> playerModelMap = new HashMap<>();

    public PlayerManager(int maxCount) {
        this.maxCount = maxCount;
    }


    public void addPlayer(PlayerModel playerModel) {
        this.playerModelMap.putIfAbsent(playerModel.getPlayerId(), playerModel);
        this.current++;
    }

    public boolean exist(Integer playerId) {
        return playerModelMap.containsKey(playerId);
    }

    public void delPlayer(Integer playerId) {
        this.playerModelMap.remove(playerId);
        this.current--;
    }

    public PlayerModel getPlayerModel(Integer playerId) {
        return playerModelMap.get(playerId);
    }

    public boolean canJoin() {
        return current < maxCount;
    }


}
