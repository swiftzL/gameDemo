package com.zl.server.play.quest.context;

import com.zl.server.play.base.commons.OperationType;
import com.zl.server.play.quest.action.LevelUpQuestAction;
import com.zl.server.play.quest.action.LoginQuestAction;
import com.zl.server.play.quest.action.QuestAction;
import com.zl.server.play.quest.condition.LevelUpQuestCondition;
import com.zl.server.play.quest.condition.LoginQuestCondition;
import com.zl.server.play.quest.condition.QuestCondition;
import com.zl.server.play.quest.commons.QuestType;
import com.zl.server.play.quest.resource.Award;
import com.zl.server.play.quest.resource.QuestConfig;
import com.zl.server.play.quest.resource.QuestResource;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务配置表
 */

public class QuestResourceContext {

    public static Map<Integer, QuestResource> allQuestConfigMap = new HashMap<>();
    public static Map<Integer, QuestConfig> mainQuestConfigMap = new HashMap<>();



    static {


        //升级
        QuestResource questResource1 = new QuestResource();
        QuestAction questAction1 = new LevelUpQuestAction();
        questResource1.setQuestAction(questAction1);
        QuestCondition questCondition1 = new LevelUpQuestCondition(OperationType.Upgrade.getCode(), 24);
        questResource1.setQuestCondition(questCondition1);
        questResource1.setId(1);
        questResource1.setQuestName("提升等级到24级");
        questResource1.setType(QuestType.Main.getCode());
        questResource1.setAward( new Award(1, 2));


        //登录
        QuestResource questResource2 = new QuestResource();
        QuestAction loginQuestAction2 = new LoginQuestAction();
        questResource2.setQuestAction(loginQuestAction2);
        QuestCondition loginQuestCondition = new LoginQuestCondition(OperationType.Login.getCode(), 2);
        questResource2.setQuestCondition(loginQuestCondition);
        questResource2.setId(2);
        questResource2.setType(QuestType.Main.getCode());
        questResource2.setQuestName("登录两次");
        questResource2.setAward(new Award(2, 2));

        mainQuestConfigMap.put(1, questResource1);
        mainQuestConfigMap.put(2, questResource2);

        allQuestConfigMap.put(1, questResource1);
        allQuestConfigMap.put(2, questResource2);
    }

    public static Map<Integer, QuestConfig> mainQuestResources() {
        return mainQuestConfigMap;
    }
}
