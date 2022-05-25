package com.zl.server.play.quest.service;

import com.zl.common.message.NetMessage;
import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.play.quest.event.QuestEvent;
import com.zl.server.play.quest.event.QuestEventType;
import com.zl.server.play.quest.model.Quest;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.quest.model.QuestBox;
import com.zl.server.play.quest.model.QuestModel;
import com.zl.server.resource.quest.QuestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class QuestServiceImpl implements QuestService {

    @Storage
    private EntityCache<Integer, Quest> questEntityCache;
    private Map<QuestEventType, QuestProcessor> questProcessorMap;
    private Map<Integer, List<QuestBox>> idToQuest = new HashMap<>();

    @Autowired
    public void setQuestProcessorMap(Set<QuestProcessor> questProcessors) {
        this.questProcessorMap = new HashMap<>();
        for (QuestProcessor questProcessor : questProcessors) {
            this.questProcessorMap.put(questProcessor.getQuestType(), questProcessor);
        }
    }

    public NetMessage showTask(NetConnection netConnection) {
        Integer id = netConnection.getAttr("id", Integer.class);
        if (id == null) {
            return new MR_Response("当前用户未登录");
        }
        Quest quest = questEntityCache.loadOrCreate(id);
        return quest.getModel();
    }

    public void handleQuestEvent(QuestEvent questEvent) {
        Integer playerId = questEvent.getPlayerId();
        Quest quest = questEntityCache.loadOrCreate(playerId);
        QuestBox model = quest.getModel();
        Set<QuestEventType> questEventTypeSet = QuestEventType.getQuestEventTypeSet();
        for (QuestEventType questEventType : questEventTypeSet) {
            QuestProcessor questProcessor = getQuestProcessor(questEventType);
            if (questProcessor == null) {
                continue;
            }
            for (QuestModel questModel : model.getQuestModels()) {
                questProcessor.finish(playerId, questModel.getTaskId(), quest, questModel, null);
            }
        }
    }

    private QuestProcessor getQuestProcessor(QuestEventType eventType) {
        return questProcessorMap.get(eventType);
    }
}
