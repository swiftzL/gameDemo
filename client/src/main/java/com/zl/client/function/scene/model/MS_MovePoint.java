package com.zl.client.function.scene.model;

import com.zl.common.message.SceneNetMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MS_MovePoint implements SceneNetMessage {
    private Point point;
}
