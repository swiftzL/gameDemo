package com.zl.server.play.quest.condition;

import com.zl.server.play.quest.model.QuestStorage;

public interface QuestCondition {

    boolean verify(Integer playerId, QuestStorage questStorage, Object resource);

    int getOperationType();

    int getCurrent(Integer playerId);//当前进度

    int getMaxCount();//最大进度

    void updateProgress(Integer playerId, int questId);//更新进度
}
