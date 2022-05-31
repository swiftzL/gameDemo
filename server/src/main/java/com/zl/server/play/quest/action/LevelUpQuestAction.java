package com.zl.server.play.quest.action;

import com.zl.server.play.quest.model.QuestStorage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LevelUpQuestAction implements QuestAction {

    @Override
    public void action(QuestStorage questStorage) {
        questStorage.setTaskStatus(1);
        questStorage.setCurrent(questStorage.getCurrent() + 1);
        log.info("等级升级任务完成");
    }
}
