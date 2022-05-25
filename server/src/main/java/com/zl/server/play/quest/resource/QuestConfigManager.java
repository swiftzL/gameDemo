package com.zl.server.play.quest.resource;

import com.zl.server.resource.quest.QuestCondition;
import com.zl.server.resource.quest.QuestConfig;
import com.zl.server.resource.quest.QuestResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QuestConfigManager {

    private Map<Integer, QuestConfig> questConfigMap = new HashMap<>();

    @Autowired
    public void setQuestConfigMap(List<QuestResource> list) {
        for (QuestResource questResource : list) {
            questConfigMap.put(questResource.getId(), questResource);
        }
    }

    public QuestConfig getconfig(Integer questId) {
        return this.questConfigMap.get(questId);
    }
}
