package com.zl.server.play.fight.packet;

import com.zl.common.message.NetMessage;
import com.zl.common.message.SceneNetMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MS_Attack implements SceneNetMessage {
    private Integer attackedPlayerId;
}
