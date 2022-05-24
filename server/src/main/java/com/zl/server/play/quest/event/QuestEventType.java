package com.zl.server.play.quest.event;

import com.zl.server.resource.quest.QuestProcessor;

import java.util.HashSet;
import java.util.Set;

public enum QuestEventType {
    Main(1);

    private int code;
    private static Set<QuestEventType> questEventTypeSet = new HashSet<>();

    QuestEventType(int code) {
        this.code = code;
    }

    static {
        for (QuestEventType questEventType : values()) {
            questEventTypeSet.add(questEventType);
        }
    }

    public int getCode() {
        return code;
    }

    public static Set<QuestEventType> getQuestEventTypeSet() {
        return questEventTypeSet;
    }


}
