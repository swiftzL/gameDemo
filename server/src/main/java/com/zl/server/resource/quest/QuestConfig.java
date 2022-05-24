package com.zl.server.resource.quest;

/**
 * 任务配置
 */
public interface QuestConfig {

    QuestCondition getFinishCondition();

    int getType();
}
