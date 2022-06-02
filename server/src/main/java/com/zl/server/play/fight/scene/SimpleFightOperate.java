package com.zl.server.play.fight.scene;

import com.zl.server.play.fight.player.PlayerModel;

public class SimpleFightOperate implements FightOperate {
    @Override
    public void pk(PlayerModel attack, PlayerModel attacked) {
        int deduct = attack.getAttack() - attacked.getDefense();
        if (deduct > 0) {
            attacked.setHp(attacked.getHp() - deduct);
        }
    }
}
