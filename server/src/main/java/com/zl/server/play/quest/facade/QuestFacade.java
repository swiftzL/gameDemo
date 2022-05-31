package com.zl.server.play.quest.facade;

import com.zl.common.message.NetMessage;
import com.zl.server.netty.anno.NetMessageHandler;
import com.zl.server.netty.anno.NetMessageInvoke;
import com.zl.server.commons.Command;
import com.zl.server.netty.anno.Param;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.play.quest.event.QuestEvent;
import com.zl.server.play.quest.packet.MS_Quest;
import com.zl.server.play.quest.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

@NetMessageHandler
public class QuestFacade {

    @Autowired
    private QuestService questService;

    @NetMessageInvoke(Command.ShowTask)
    public void showTask(@Param("id")Integer playerId, NetConnection netConnection) {
        questService.showTask(playerId,netConnection);
    }


    @NetMessageInvoke(Command.DrawAward)
    public void drawAward(@Param("id") Integer playerId, MS_Quest req) throws Exception {
        questService.drawAward(playerId, req);
    }

    @NetMessageInvoke(Command.AcceptQuest)
    public void acceptQuest(@Param("id") Integer playerId, MS_Quest req) {
        questService.acceptQuest(playerId, req);
    }


    @EventListener
    public void handleQuestEvent(QuestEvent questEvent) {
        questService.handleQuestEvent(questEvent);
    }

}
