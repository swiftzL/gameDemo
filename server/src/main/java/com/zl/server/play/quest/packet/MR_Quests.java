package com.zl.server.play.quest.packet;

import com.zl.common.message.NetMessage;
import com.zl.server.play.quest.model.QuestStorage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MR_Quests implements NetMessage {
    private List<QuestStorage> questStorages;
}
