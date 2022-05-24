package com.zl.server.resource.quest;

import com.zl.server.play.quest.packet.QuestModel;

public interface QuestCondition {

    boolean verify(Integer playerId, QuestModel questModel);

}
