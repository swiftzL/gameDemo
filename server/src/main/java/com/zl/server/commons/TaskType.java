package com.zl.server.commons;

public enum TaskType {

    LevelUp(1);


    private int code;


    TaskType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
