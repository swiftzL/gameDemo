package com.zl.server.play.quest.commons;

import java.util.HashSet;
import java.util.Set;

public enum QuestType {
    Main(1);

    private int code;
    private static Set<QuestType> questEventTypeSet = new HashSet<>();

    QuestType(int code) {
        this.code = code;
    }

    static {
        for (QuestType questEventType : values()) {
            questEventTypeSet.add(questEventType);
        }
    }

    public int getCode() {
        return code;
    }
}
