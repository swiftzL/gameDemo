package com.zl.server.play.bag.packet;

import com.zl.common.message.NetMessage;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MS_Props implements NetMessage {
    private int propsId;
    private int num;
}
