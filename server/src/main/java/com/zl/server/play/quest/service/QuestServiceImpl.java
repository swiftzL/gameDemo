package com.zl.server.play.quest.service;

import com.zl.common.message.NetMessage;
import com.zl.server.cache.EntityCache;
import com.zl.server.play.quest.model.Quest;
import com.zl.server.netty.NetConnection;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.quest.packet.QuestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class QuestServiceImpl implements QuestService {

    @Autowired
    @Qualifier("taskEntityCache")
    EntityCache<Integer, Quest> entityCache;

    public NetMessage showTask(NetConnection netConnection) {
        Integer id = netConnection.getAttr("id", Integer.class);
        if (id == null) {
            return new MR_Response("当前用户未登录");
        }
        Quest quest = entityCache.loadOrCreate(id);
        return new MR_Response(quest.getModel().toString());
    }
}
