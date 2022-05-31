package com.zl.server.play.quest.config;

import com.zl.server.play.player.OperationType;
import com.zl.server.play.quest.action.LevelUpQuestAction;
import com.zl.server.play.quest.action.LoginQuestAction;
import com.zl.server.play.quest.action.QuestAction;
import com.zl.server.play.quest.condition.LevelUpQuestCondition;
import com.zl.server.play.quest.condition.LoginQuestCondition;
import com.zl.server.play.quest.condition.QuestCondition;
import com.zl.server.play.quest.event.QuestType;
import com.zl.server.play.quest.resource.QuestConfig;
import com.zl.server.play.quest.resource.QuestResource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务配置表
 */

public class QuestResourceConfig {

    public static Map<Integer, QuestConfig> mainQuestResources() {
        Map<Integer, QuestConfig> questConfigMap = new HashMap<>();

        //升级
        QuestResource questResource1 = new QuestResource();
        QuestAction questAction1 = new LevelUpQuestAction();
        questResource1.setQuestAction(questAction1);
        QuestCondition questCondition1 = new LevelUpQuestCondition(OperationType.Upgrade.getCode(), 24);
        questResource1.setQuestCondition(questCondition1);
        questResource1.setId(1);
        questResource1.setType(QuestType.Main.getCode());

        //登录
        QuestResource questResource2 = new QuestResource();
        QuestAction loginQuestAction2 = new LoginQuestAction();
        questResource2.setQuestAction(loginQuestAction2);
        QuestCondition loginQuestCondition = new LoginQuestCondition(OperationType.Login.getCode());
        questResource2.setQuestCondition(loginQuestCondition);
        questResource2.setId(2);
        questResource2.setType(QuestType.Main.getCode());
        questConfigMap.put(1, questResource1);
        questConfigMap.put(2, questResource2);
        return questConfigMap;
    }
}
