package com.zl.server.play.quest.condition;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.player.PlayerContext;
import com.zl.server.play.quest.model.Quest;
import com.zl.server.play.quest.model.QuestStorage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;


@Getter
@AllArgsConstructor
public class LevelUpQuestCondition implements QuestCondition {
    private int type;
    private int leve;

    @Override
    public boolean verify(Integer playerId, QuestStorage questStorage, Object resource) {
        Integer currentLevel = PlayerContext.INSTANCE.getLevel(playerId);
        return currentLevel == leve;
    }

    @Override
    public int getOperationType() {
        return type;
    }

    @Override
    public int getCurrent(Integer playerId) {
        return PlayerContext.INSTANCE.getLevel(playerId);
    }

    @Override
    public int getMaxCount() {
        return this.leve;
    }
}
