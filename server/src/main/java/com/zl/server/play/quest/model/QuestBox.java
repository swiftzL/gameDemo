package com.zl.server.play.quest.model;

import com.zl.common.message.NetMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class QuestBox implements NetMessage {
    private List<QuestModel> questModels;

}
