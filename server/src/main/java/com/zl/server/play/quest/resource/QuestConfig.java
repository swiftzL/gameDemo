package com.zl.server.play.quest.resource;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.play.base.model.Account;
import com.zl.server.resource.quest.QuestAction;
import com.zl.server.resource.quest.QuestCondition;
import com.zl.server.resource.quest.QuestResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class QuestConfig {
    @Bean
    public List<QuestResource> questResources(QuestCondition questCondition, QuestAction questAction) {
        List<QuestResource> list = new ArrayList<>();
        QuestResource questResource = new QuestResource();
        questResource.setId(1);
        questResource.setType(1);
        questResource.setQuestCondition(questCondition);
        questResource.setQuestAction(questAction);
        list.add(questResource);
        return list;
    }
}
