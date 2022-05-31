package com.zl.server.play.quest.resource;

import com.zl.server.play.quest.action.QuestAction;
import com.zl.server.play.quest.condition.QuestCondition;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务配置表
 */
@Setter
@Getter
public class QuestResource implements QuestConfig {

    /**
     * 任务id
     */
    private int id;

    /**
     * 任务类型 主线 支线
     */
    private int type;

    private QuestCondition questCondition;
    private QuestAction questAction;

    @Override
    public QuestCondition getFinishCondition() {
        return this.questCondition;
    }

    @Override
    public int getType() {
        return this.type;
    }
}
