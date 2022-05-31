package com.zl.server.play.quest.condition;

import com.zl.server.play.quest.model.QuestStorage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginQuestCondition implements QuestCondition {

    private int type;

    @Override
    public boolean verify(Integer playerId, QuestStorage questStorage, Object resource) {
        return true;
    }

    @Override
    public int getOperationType() {
        return type;
    }
}
