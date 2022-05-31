package com.zl.server.play.quest.condition;

import com.zl.server.play.player.PlayerServiceContext;
import com.zl.server.play.player.service.PlayerService;
import com.zl.server.play.quest.model.QuestStorage;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class LevelUpQuestCondition implements QuestCondition {
    private int type;
    private int leve;

    @Override
    public boolean verify(Integer playerId, QuestStorage questStorage, Object resource) {
        PlayerService playerService = PlayerServiceContext.getPlayerService();
        Integer currentLevel = playerService.getLevel(playerId);
        return currentLevel == leve;
    }

    @Override
    public int getOperationType() {
        return type;
    }

    @Override
    public int getCurrent(Integer playerId) {
        PlayerService playerService = PlayerServiceContext.getPlayerService();
        return playerService.getLevel(playerId);
    }

    @Override
    public int getMaxCount() {
        return this.leve;
    }
}
