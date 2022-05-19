package com.zl.common.common;

public enum Command {

    Login(1,"用户登录"),
    CreateAccount(2,"用户注册"),
    AccountInfo(3,"用户信息");
    private int code;
    private String desc;

    Command(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
