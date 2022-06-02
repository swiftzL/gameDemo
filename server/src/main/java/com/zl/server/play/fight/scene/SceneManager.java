package com.zl.server.play.fight.scene;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SceneManager {

    private static AtomicInteger sceneId = new AtomicInteger();
    private static Map<Integer, Scene> sceneMap = new ConcurrentHashMap<>();

    public static Scene createScene() {
        return null;
    }
}
