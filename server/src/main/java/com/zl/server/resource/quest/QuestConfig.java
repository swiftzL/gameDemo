package com.zl.server.resource.quest;

import com.zl.server.play.quest.model.QuestModel;

/**
 * 任务配置
 */
public interface QuestConfig {

    QuestCondition getFinishCondition();
    QuestAction getQuestAction();
    int getType();
}
