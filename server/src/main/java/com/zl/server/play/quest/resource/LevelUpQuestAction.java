package com.zl.server.play.quest.resource;

import com.zl.server.play.quest.model.QuestModel;
import com.zl.server.resource.quest.QuestAction;
import org.springframework.stereotype.Component;

@Component
public class LevelUpQuestAction implements QuestAction {

    @Override
    public void action(QuestModel questModel) {
        questModel.setTaskStatus(1);
        questModel.setCurrent(questModel.getCurrent() + 1);
    }
}
