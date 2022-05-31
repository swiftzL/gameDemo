package com.zl.server;

import com.zl.server.play.player.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GameContext {

    public static GameContext INSTANCE;

    @Autowired
    private PlayerService playerService;

    @PostConstruct
    public void init() {
        INSTANCE = this;
    }

    public static PlayerService getPlayerService() {
        return INSTANCE.playerService;
    }
}
