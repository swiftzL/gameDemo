package com.zl.server.play.quest.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class QuestEvent extends ApplicationEvent {
    public QuestEvent(Object source) {
        super(source);
    }

    private Integer playerId;
    private Object params;

    public static QuestEvent valueOf(Integer playerId, Object params) {
        QuestEvent event = new QuestEvent(playerId);
        event.setPlayerId(playerId);
        event.setParams(params);
        return event;
    }

}
