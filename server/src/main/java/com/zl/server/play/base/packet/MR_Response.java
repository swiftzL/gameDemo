package com.zl.server.play.base.packet;

import com.zl.common.message.NetMessage;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MR_Response implements NetMessage {
    private String content;
}
