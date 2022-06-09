package com.zl.server.play.quest.processor;

import com.zl.server.play.quest.commons.QuestType;
import com.zl.server.play.quest.model.Quest;
import com.zl.server.play.quest.model.QuestStorage;
import com.zl.server.play.quest.resource.QuestConfig;

public interface QuestProcessor {

    QuestType getQuestType();

    QuestConfig getQuestConfig(Integer id);

    void finish(Integer playerId, int questId, Quest quest, QuestStorage questModel, Object resource, Object param);

}
