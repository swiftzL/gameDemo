package com.zl.server.play.base.event;

import com.zl.server.play.player.OperationType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class LoginEvent extends PlayerEvent {
    public LoginEvent(Object source) {
        super(source);
    }

    public static LoginEvent valueOf(Integer playerId, Object source, Object param) {
        LoginEvent loginEvent = new LoginEvent(source);
        loginEvent.setParam(param);
        loginEvent.setPlayerId(playerId);
        loginEvent.setType(OperationType.Login.getCode());
        return loginEvent;
    }
}
