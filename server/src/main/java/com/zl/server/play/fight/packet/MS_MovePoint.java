package com.zl.server.play.fight.packet;

import com.zl.common.message.SceneNetMessage;
import com.zl.server.play.fight.model.Point;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MS_MovePoint implements SceneNetMessage {
    private Point point;
}
