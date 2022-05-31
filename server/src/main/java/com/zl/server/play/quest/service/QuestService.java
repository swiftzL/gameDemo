package com.zl.server.play.quest.service;

import com.zl.common.message.NetMessage;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.play.quest.event.QuestEvent;
import com.zl.server.play.quest.packet.MS_Quest;
import org.springframework.context.event.EventListener;

public interface QuestService {
    void showTask(Integer playerId, NetConnection netConnection);

    void handleQuestEvent(QuestEvent questEvent);

    void drawAward(Integer playerId, MS_Quest req) throws Exception;

    void acceptQuest(Integer playerId, MS_Quest req);
}
