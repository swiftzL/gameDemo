package com.zl.server.play.quest.event;

import lombok.Setter;
import org.springframework.context.ApplicationEvent;

public abstract class PlayerEvent extends ApplicationEvent {
    @Setter
    private Integer playerId;

    public PlayerEvent(Object source) {
        super(source);
    }
}
