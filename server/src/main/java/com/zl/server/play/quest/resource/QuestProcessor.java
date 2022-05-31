package com.zl.server.play.quest.resource;

import com.zl.server.play.quest.commons.QuestType;
import com.zl.server.play.quest.model.Quest;
import com.zl.server.play.quest.model.QuestStorage;

public interface QuestProcessor {

    QuestType getQuestType();

    QuestConfig getQuestConfig(Integer id);

    void finish(Integer playerId, int questId, Quest quest, QuestStorage questModel, Object resource);

}
