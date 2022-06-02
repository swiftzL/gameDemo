package com.zl.server.play.fight.player;

import lombok.Getter;
import lombok.Setter;

import java.awt.Point;

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
