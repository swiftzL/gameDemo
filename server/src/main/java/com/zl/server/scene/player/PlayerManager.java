package com.zl.server.scene.player;


import com.zl.server.scene.player.model.PlayerModel;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private volatile int current;
    private volatile int maxCount;

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
