package com.zl.server.play.quest.packet;

import com.alibaba.fastjson.annotation.JSONField;
import com.zl.common.message.NetMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class QuestBox implements NetMessage {
    private List<QuestModel> questModels;

}
