package com.zl.server.play.equip.packet;

import com.zl.common.message.NetMessage;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MR_UseEquipment implements NetMessage {
    private int modelId;
}
