package com.zl.server.play.quest.resource;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.quest.model.Quest;
import com.zl.server.play.quest.packet.QuestModel;
import com.zl.server.resource.quest.QuestCondition;
import org.springframework.stereotype.Component;

@Component
public class LevelUpQuestCondition implements QuestCondition {

    @Storage
    private EntityCache<Integer, Account> accountEntityCache;

    @Storage
    private EntityCache<Integer, Quest> questEntityCache;


    @Override
    public boolean verify(Integer playerId, QuestModel questModel) {
        Account account = accountEntityCache.loadOrCreate(playerId);
        if (account == null) {
            return false;
        }
        if (account.getLevel() == 14) {
            questModel.setTaskStatus(1);
            questModel.setCurrent(questModel.getCurrent() + 1);
        }
        return true;
    }
}
