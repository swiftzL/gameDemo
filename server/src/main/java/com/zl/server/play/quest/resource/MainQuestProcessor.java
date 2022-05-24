package com.zl.server.play.quest.resource;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.play.quest.event.QuestEventType;
import com.zl.server.play.quest.model.Quest;
import com.zl.server.play.quest.packet.QuestModel;
import com.zl.server.resource.quest.QuestCondition;
import com.zl.server.resource.quest.QuestConfig;
import com.zl.server.resource.quest.QuestProcessor;
import com.zl.server.resource.quest.QuestResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MainQuestProcessor implements QuestProcessor {

    private Map<Integer, QuestConfig> questConfigMap = new HashMap<>();
    @Storage
    private EntityCache<Integer, Quest> entityCache;


    @Autowired
    public void setQuestConfigMap(List<QuestResource> list) {
        for (QuestResource questResource : list) {
            questConfigMap.put(questResource.getId(), questResource);
        }
    }

    @Override
    public QuestEventType getQuestType() {
        return QuestEventType.Main;
    }

    @Override
    public void finish(Integer playerId, int questId, Quest quest, QuestModel questModel) {
        QuestConfig questConfig = this.questConfigMap.get(questId);
        if (questConfig == null) {
            log.warn("questId" + questId + "is not found");
        }
        QuestCondition finishCondition = questConfig.getFinishCondition();
        if (finishCondition.verify(playerId, questModel)) {
            entityCache.writeBack(quest);
        }
    }
}
