package com.zl.server.play.bag.packet;

import com.zl.common.message.NetMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MS_Equipment implements NetMessage {
    private int modelId;
}
