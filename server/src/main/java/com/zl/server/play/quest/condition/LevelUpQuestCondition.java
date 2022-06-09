package com.zl.server.play.quest.condition;

import com.zl.server.GameContext;
import com.zl.server.play.player.service.PlayerService;
import com.zl.server.play.quest.model.QuestStorage;
import com.zl.server.play.quest.service.QuestService;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class LevelUpQuestCondition implements QuestCondition<Integer> {
    private int type;
    private int leve;

    @Override
    public boolean verify(Integer playerId, QuestStorage questStorage, Object resource) {
        PlayerService playerService = GameContext.getPlayerService();
        Integer currentLevel = playerService.getLevel(playerId);
        return currentLevel >= leve;
    }

    @Override
    public int getOperationType() {
        return type;
    }

    @Override
    public int getCurrent(Integer playerId) {
        PlayerService playerService = GameContext.getPlayerService();
        return playerService.getLevel(playerId);
    }

    @Override
    public int getMaxCount() {
        return this.leve;
    }

    @Override
    public void updateProgress(Integer playerId, int questId, Integer param) {
        QuestService questService = GameContext.getQuestService();
        questService.updateProgress(playerId, questId, param);
    }
}
