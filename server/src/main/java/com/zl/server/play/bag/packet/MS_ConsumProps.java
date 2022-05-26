package com.zl.server.play.bag.packet;

import com.zl.common.message.NetMessage;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MS_ConsumProps implements NetMessage {
    private int modelId;
    private int[] idxs;
    private int num;
}
