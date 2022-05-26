package com.zl.server.play.bag.item;

public enum ItemType {


    Drug(1);

    private int code;


    ItemType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
