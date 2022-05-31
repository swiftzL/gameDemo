package com.zl.server.play.quest.packet;

import com.zl.common.message.NetMessage;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MS_Quest implements NetMessage {
    private Integer questId;
}
