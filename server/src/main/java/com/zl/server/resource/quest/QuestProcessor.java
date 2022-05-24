package com.zl.server.resource.quest;

import com.zl.server.play.quest.event.QuestEventType;
import com.zl.server.play.quest.model.Quest;
import com.zl.server.play.quest.packet.QuestModel;

public interface QuestProcessor {

    QuestEventType getQuestType();

    void finish(Integer playerId, int questId, Quest quest, QuestModel questModel);

}
