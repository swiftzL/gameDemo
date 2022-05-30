package com.zl.server.play.quest.condition;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.quest.model.Quest;
import com.zl.server.play.quest.model.QuestModel;
import com.zl.server.resource.quest.QuestCondition;
import org.springframework.stereotype.Component;

@Component
public class LevelUpQuestCondition implements QuestCondition {

    @Storage
    private EntityCache<Integer, Account> accountEntityCache;

    @Storage
    private EntityCache<Integer, Quest> questEntityCache;

    private Integer level = 14;


    @Override
    public boolean verify(Integer playerId, QuestModel questModel, Object resource) {
        Account account = accountEntityCache.loadOrCreate(playerId);
        if (account == null) {
            return false;
        }
        return account.getLevel() == this.level;
    }
}
