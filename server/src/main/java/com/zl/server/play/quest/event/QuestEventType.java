package com.zl.server.play.quest.event;

public enum QuestEventType {
    LevelUP(1);

    private int code;

    QuestEventType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
