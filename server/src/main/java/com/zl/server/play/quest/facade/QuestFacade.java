package com.zl.server.play.quest.facade;

import com.zl.common.message.NetMessage;
import com.zl.server.anno.NetMessageHandler;
import com.zl.server.anno.NetMessageInvoke;
import com.zl.server.commons.Command;
import com.zl.server.commons.Response;
import com.zl.server.model.Task;
import com.zl.server.netty.NetConnection;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.quest.service.QuestService;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
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
