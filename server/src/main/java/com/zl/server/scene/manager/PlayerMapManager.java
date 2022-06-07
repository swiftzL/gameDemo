package com.zl.server.scene.manager;


import com.zl.server.play.fight.model.Point;
import com.zl.server.scene.model.PlayerModel;

import java.util.Random;

public class PlayerMapManager {
    private byte[][] map; //地图
    private int right;
    private int down;

    public PlayerMapManager(int right, int down) {
        this.right = right;
        this.down = down;
        Random random = new Random();
        this.map = new byte[down + 1][right + 1];
        for (int i = 0; i < down + 1; i++) {
            for (int j = 0; j < right + 1; j++) {
                map[i][j] = (byte) random.nextInt(2);
            }
        }
    }

    private boolean noMove(Point point) {
        return point.x < 0 || point.y < 0  ||point.x > right || point.y > down || map[point.x][point.y] == 1;
    }

    public boolean move(PlayerModel playerModel, Point point) {
        if (noMove(point)) {
            return false;
        }
        playerModel.setPoint(point);
        return true;
    }

}

