package com.zl.server.play.base.event;

import com.zl.server.play.base.commons.OperationType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpgradeEvent extends PlayerEvent {
    public UpgradeEvent(Object source) {
        super(source);
    }

    public static UpgradeEvent valueOf(Integer playerId, Object source, Object param) {
        UpgradeEvent upgradeEvent = new UpgradeEvent(source);
        upgradeEvent.setType(OperationType.Upgrade.getCode());
        upgradeEvent.setParam(param);
        upgradeEvent.setPlayerId(playerId);
        return upgradeEvent;
    }
}
