package com.zl.server.play.player;

public enum OperationType {
    Login(1),
    Upgrade(2);
    private int code;

    OperationType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
