package com.zl.server.scene.operate;

import com.zl.server.netty.utils.NetMessageUtil;
import com.zl.server.scene.operate.FightOperate;
import com.zl.server.scene.player.model.PlayerModel;

public class SimpleFightOperate implements FightOperate {
    @Override
    public void pk(PlayerModel attack, PlayerModel attacked) {
        if(attack.getPlayerId().equals(attacked.getPlayerId())){
            NetMessageUtil.sendMessage(attack.getPlayerId(), "不能自己攻击自己");
            return;
        }
        if (attacked.getHp() <= 0) {
            NetMessageUtil.sendMessage(attack.getPlayerId(), "当前目标已死亡 无法攻击");
            return;
        }
        int deduct = attack.getAttack() - attacked.getDefense();

        if (deduct > 0) {
            attacked.setHp(attacked.getHp() - deduct);
            NetMessageUtil.sendMessage(attack.getPlayerId(), "被攻击目标当前hp" + attacked.getHp());
        } else {
            NetMessageUtil.sendMessage(attack.getPlayerId(), "当前攻击力不足 无法攻击");
        }
    }
}
