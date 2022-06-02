package com.zl.server.play.fight.player;


import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    private Map<Integer, PlayerModel> playerModelMap = new HashMap<>();

    public void addPlayer(PlayerModel playerModel) {
        this.playerModelMap.putIfAbsent(playerModel.getPlayerId(), playerModel);
    }

    public boolean exist(Integer playerId) {
        return playerModelMap.containsKey(playerId);
    }

    public void delPlayer(Integer playerId) {
        this.playerModelMap.remove(playerId);
    }


}
