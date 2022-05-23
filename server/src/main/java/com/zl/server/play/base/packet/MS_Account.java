package com.zl.server.play.base.packet;

import com.zl.common.message.NetMessage;
import lombok.Data;

@Data
public class MS_Account implements NetMessage {
    private String username;
    private String password;
}
