package com.zl.server.play.base.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public abstract class PlayerEvent extends ApplicationEvent {

    private int type;
    private Integer playerId;
    private Object param;

    public PlayerEvent(Object source) {
        super(source);
    }
}
