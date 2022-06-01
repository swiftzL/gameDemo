package com.zl.server;

import com.zl.server.play.bag.service.BagService;
import com.zl.server.play.player.service.PlayerService;
import com.zl.server.play.quest.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GameContext {

    public static GameContext INSTANCE;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private BagService bagService;

    @Autowired
    private QuestService questService;

    @PostConstruct
    public void init() {
        INSTANCE = this;
    }

    public static PlayerService getPlayerService() {
        return INSTANCE.playerService;
    }

    public static BagService getBagService() {
        return INSTANCE.bagService;
    }

    public static QuestService getQuestService() {
        return INSTANCE.questService;
    }
}
