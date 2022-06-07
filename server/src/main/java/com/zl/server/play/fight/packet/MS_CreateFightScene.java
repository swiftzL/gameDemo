package com.zl.server.play.fight.packet;

import com.zl.common.message.NetMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MS_CreateFightScene implements NetMessage {
    private int down;
    private int right;
    private int maxCount;
}
