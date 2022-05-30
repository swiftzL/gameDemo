package com.zl.server.play.bag.packet;

import com.zl.common.message.NetMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MS_Props implements NetMessage {
    private int propsId;
    private int num;
}
