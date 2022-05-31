package com.zl.server.play.bag.packet;

import com.zl.common.message.NetMessage;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MR_UseProps implements NetMessage {
    private int[] idxs;
    private int type;
}
