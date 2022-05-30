package com.zl.server.commons;

public enum Command {

    Login(1, "用户登录"),
    CreateAccount(2, "用户注册"),
    AccountInfo(3, "用户信息"),
    ShowTask(4, "查看当前任务"),
    Upgrade(5, "升级"),
    PutProps(6, "添加道具"),
    ConsumeProps(7, "使用道具"),
    ShowBag(8,"查看背包状态"),
    UseEquipment(9,"使用装备"),
    RemoveEquipment(10,"移除装备");


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
