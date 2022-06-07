package com.zl.server.scene;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SceneManager {

    private static AtomicInteger sceneId = new AtomicInteger();
    private static Map<Integer, Scene> sceneMap = new ConcurrentHashMap<>();
    private static Map<Integer, Scene> playerToScene = new ConcurrentHashMap<>();

    public static Scene putScene( Scene scene) {
        Integer id = sceneId.incrementAndGet();
        scene.setSceneId(id);
        sceneMap.put(id, scene);
        return scene;
    }

    public static void addPlayerToScene(Integer playerId,Scene scene){
        playerToScene.put(playerId,scene);
    }

    public static void quitScene(Integer playerId) {
        Scene scene = playerToScene.remove(playerId);
        if (scene != null) {
            scene.quit(playerId);
        }
    }

    public static Scene getScene(Integer sceneId) {
        return sceneMap.get(sceneId);
    }

    public static Scene getSceneByPlayer(Integer playerId){
        return playerToScene.get(playerId);
    }
}
