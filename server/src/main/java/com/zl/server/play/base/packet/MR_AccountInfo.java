package com.zl.server.play.base.packet;

import com.zl.common.message.NetMessage;

import com.zl.server.play.base.vo.AccountVo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MR_AccountInfo implements NetMessage {
    private String username;
    private Integer level;
    private AccountVo accountVo;
}
