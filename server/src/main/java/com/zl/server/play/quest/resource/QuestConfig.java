package com.zl.server.play.quest.resource;

import com.zl.server.play.quest.action.QuestAction;
import com.zl.server.play.quest.condition.QuestCondition;

/**
 * 任务配置
 */
public interface QuestConfig {

    QuestCondition getFinishCondition();
    QuestAction getQuestAction();
    int getType();
}
