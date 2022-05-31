package com.zl.server.play.quest.condition;

import com.zl.server.play.quest.model.QuestStorage;

public interface QuestCondition {

    boolean verify(Integer playerId, QuestStorage questStorage, Object resource);
    int getOperationType();
}
