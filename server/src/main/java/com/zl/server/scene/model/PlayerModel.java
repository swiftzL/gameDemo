package com.zl.server.scene.model;

import com.zl.server.play.fight.model.Point;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlayerModel {
    private Integer playerId;
    private Point point; //玩家坐标
    private int hp;
    private int attack;
    private int defense;


    public boolean canAttack(Point point) {
        return this.point.equals(point);
    }

}
