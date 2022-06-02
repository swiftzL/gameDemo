package com.zl.server.play.fight.player;

import com.zl.server.play.fight.player.PlayerModel;

import java.awt.*;

public class PlayerMapManager {
    private int[][] map; //地图
    private int right;
    private int down;

    public boolean canAttack(Point point1, Point point2) {
        return point1.equals(point2);
    }

    private boolean canMove(Point point) {
        return point.x < 0 || point.y < 0 || point.x > right || point.y > down || map[point.x][point.y] == 1;
    }

    public boolean move(PlayerModel playerModel, Point point) {
        if (!canMove(point)) {
            return false;
        }
        playerModel.setPoint(point);
        return true;
    }

}

