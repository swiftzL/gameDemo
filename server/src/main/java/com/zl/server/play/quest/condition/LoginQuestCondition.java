package com.zl.server.play.quest.condition;

import com.zl.server.GameContext;
import com.zl.server.play.quest.model.QuestStorage;
import com.zl.server.play.quest.service.QuestService;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginQuestCondition implements QuestCondition<Integer> {

    private int type;
    private int count;

    @Override
    public boolean verify(Integer playerId, QuestStorage questStorage, Object resource) {
        return questStorage.getCurrent() >= questStorage.getMaxCount();
    }

    @Override
    public int getOperationType() {
        return type;
    }

    @Override
    public int getCurrent(Integer playerId) {
        return 0;
    }

    @Override
    public int getMaxCount() {
        return this.count;
    }

    @Override
    public void updateProgress(Integer playerId, int questId, Integer param) {
        QuestService questService = GameContext.getQuestService();
        questService.updateProgress(playerId, questId, 1);
    }

}
