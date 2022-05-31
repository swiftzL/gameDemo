package com.zl.server.play.quest.config;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.play.quest.action.QuestAction;
import com.zl.server.play.quest.event.QuestType;
import com.zl.server.play.quest.model.Quest;
import com.zl.server.play.quest.condition.QuestCondition;
import com.zl.server.play.quest.model.QuestStorage;
import com.zl.server.play.quest.resource.QuestConfig;
import com.zl.server.play.quest.resource.QuestProcessor;
import com.zl.server.play.quest.resource.QuestResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class MainQuestProcessor implements QuestProcessor {

    private Map<Integer, QuestConfig> questResourceMap = QuestResourceConfig.mainQuestResources();
    @Storage
    private EntityCache<Integer, Quest> entityCache;


    @Override
    public QuestType getQuestType() {
        return QuestType.Main;
    }

    public QuestConfig getQuestConfig(Integer id){
        return questResourceMap.get(id);
    }

    @Override
    public void finish(Integer playerId, int questId, Quest quest, QuestStorage questStorage, Object resource) {
        QuestConfig questConfig = questResourceMap.get(questId);
        QuestCondition finishCondition = questConfig.getFinishCondition();
        if (finishCondition.verify(playerId, questStorage, resource)) {
            QuestAction questAction = questConfig.getQuestAction();
            questAction.action(questStorage);
            entityCache.writeBack(quest);
        }
    }
}