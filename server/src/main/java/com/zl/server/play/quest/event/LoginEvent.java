package com.zl.server.play.quest.event;

import org.springframework.context.ApplicationEvent;

public class LoginEvent extends PlayerEvent {

    public LoginEvent(Object source) {
        super(source);
    }

    public static LoginEvent LoginEvent(Object source, Integer playerId) {
        LoginEvent loginEvent = new LoginEvent(source);
        loginEvent.setPlayerId(playerId);
        return loginEvent;
    }

}
