package com.zl.server.play.fight.packet;

import com.zl.common.message.NetMessage;
import com.zl.common.message.SceneNetMessage;
import com.zl.server.play.fight.model.Point;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class MR_MovePoint implements SceneNetMessage {
    private Point point;
}
