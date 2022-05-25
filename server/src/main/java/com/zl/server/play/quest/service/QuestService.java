package com.zl.server.play.quest.service;

import com.zl.common.message.NetMessage;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.play.quest.event.QuestEvent;
import org.springframework.context.event.EventListener;

public interface QuestService {
    NetMessage showTask(NetConnection netConnection);
    void handleQuestEvent(QuestEvent questEvent);
}
