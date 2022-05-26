package com.zl.server.play.bag.packet;

import com.zl.common.message.NetMessage;
import com.zl.server.play.bag.resource.Item;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MR_BagStatus implements NetMessage {
    private int bagCap;
    private Item[] items;
}
