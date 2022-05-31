package com.zl.server.play.quest.config;

import com.zl.server.play.quest.model.QuestStorage;

import java.util.HashMap;
import java.util.Map;

public class QuestManager {

    public static Map<Integer, QuestStorage> questStorageMap = new HashMap<>();

    public static QuestStorage getQuest(Integer id) {
        QuestStorage questStorage = questStorageMap.get(id);
        QuestStorage questStorage1 = new QuestStorage();
        questStorage1.setTaskStatus(questStorage.getTaskStatus());
        questStorage1.setTaskName(questStorage.getTaskName());
        questStorage1.setCurrent(questStorage.getCurrent());
        questStorage1.setMaxCount(questStorage.getMaxCount());
        questStorage1.setTaskType(questStorage.getTaskType());
        questStorage1.setIsReceive(questStorage.getIsReceive());
        questStorage1.setTaskId(questStorage.getTaskId());
        return questStorage1;
    }

    static {
        QuestStorage questStorage = new QuestStorage();
        questStorage.setTaskName("等级提升至25级");
        questStorage.setTaskId(1);
        questStorage.setTaskStatus(0);
        questStorage.setCurrent(0);
        questStorage.setMaxCount(1);
        questStorage.setIsReceive(0);
        questStorage.setTaskType(1);

        QuestStorage questStorage1 = new QuestStorage();
        questStorage1.setTaskName("登录两次");
        questStorage1.setTaskId(2);
        questStorage1.setTaskType(1);
        questStorage1.setCurrent(0);
        questStorage1.setMaxCount(2);
        questStorage1.setTaskStatus(0);
        questStorage1.setIsReceive(0);

        questStorageMap.put(1, questStorage);
        questStorageMap.put(2, questStorage1);
    }
}
