package com.zl.server.commons;

public enum Command {
    Heartbeat(0, "心跳"),
    Login(1, "用户登录"),
    CreateAccount(2, "用户注册"),
    AccountInfo(3, "用户信息"),
    ShowTask(4, "查看当前任务"),
    Upgrade(5, "升级"),
    PutProps(6, "添加道具"),
    ConsumeProps(7, "使用道具"),
    ShowBag(8, "查看背包状态"),
    UseEquipment(9, "使用装备"),
    RemoveEquipment(10, "移除装备"),
    AcceptQuest(11, "接受任务"),
    DrawAward(12, "完成任务"),
    CreateFightScene(13, "创建战斗场景"),
    JoinScene(14, "加入场景"),
    QuitScene(15, "退出场景"),
    MovePoint(16, "移动坐标"),
    Attack(17, "攻击目标"),
    LOGOUT(18, "退出");

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
