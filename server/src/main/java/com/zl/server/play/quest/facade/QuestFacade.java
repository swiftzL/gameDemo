package com.zl.server.play.quest.facade;

import com.zl.common.message.NetMessage;
import com.zl.server.netty.anno.NetMessageHandler;
import com.zl.server.netty.anno.NetMessageInvoke;
import com.zl.server.commons.Command;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.play.quest.packet.QuestBox;
import com.zl.server.play.quest.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;

@NetMessageHandler
public class QuestFacade {

    @Autowired
    private QuestService questService;

    @NetMessageInvoke(Command.ShowTask)
    public NetMessage showTask(NetConnection netConnection) {
        return questService.showTask(netConnection);
    }

}
