package com.zl.server.play.quest.service;

import com.alibaba.fastjson.JSON;
import com.zl.common.message.NetMessage;
import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.commons.Command;
import com.zl.server.netty.anno.NetMessageInvoke;
import com.zl.server.play.quest.event.QuestEvent;
import com.zl.server.play.quest.model.Quest;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.quest.packet.QuestBox;
import com.zl.server.play.quest.packet.QuestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class QuestServiceImpl implements QuestService {

    @Storage
    private EntityCache<Integer, Quest> questEntityCache;


    public NetMessage showTask(NetConnection netConnection) {
        Integer id = netConnection.getAttr("id", Integer.class);
        if (id == null) {
            return new MR_Response("当前用户未登录");
        }
        Quest quest = questEntityCache.loadOrCreate(id);
        return quest.getModel();
    }

    @EventListener
    public void handleQuestEvent(QuestEvent<Integer> questEvent) {
        Integer playerId = questEvent.getPlayerId();
        Quest quest = questEntityCache.loadOrCreate(playerId);
        QuestBox model = quest.getModel();
        Integer params = questEvent.getParams();
        for (QuestDto questDto : model.getQuestDtos()) {
            if (params.equals(questDto.getTaskType())) {
                questDto.setTaskStatus(1);
                questEntityCache.writeBack(quest);
                break;
            }
        }
    }
}
