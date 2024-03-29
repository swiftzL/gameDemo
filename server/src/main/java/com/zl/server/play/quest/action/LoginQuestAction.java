package com.zl.server.play.quest.action;

import com.zl.server.play.quest.commons.QuestConstants;
import com.zl.server.play.quest.model.QuestStorage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginQuestAction implements QuestAction {

    @Override
    public void action(QuestStorage questStorage) {
        if (questStorage.getCurrent() >= questStorage.getMaxCount()) {
            log.info("登录任务完成");
            questStorage.setTaskStatus(QuestConstants.YES);
        }
    }
}
