package com.zl.server.commons;

public enum Command {

    Login(1, "用户登录"),
    CreateAccount(2, "用户注册"),
    AccountInfo(3, "用户信息"),
    ShowTask(4, "查看当前任务"),
    Upgrade(5, "升级");
    private int code;
    private String desc;

    Command(int code, String desc) {
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
