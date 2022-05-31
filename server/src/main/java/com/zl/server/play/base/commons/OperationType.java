package com.zl.server.play.base.commons;

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
