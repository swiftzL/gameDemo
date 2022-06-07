package com.zl.server.play.fight.service;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.commons.Command;
import com.zl.server.commons.Constants;
import com.zl.server.netty.anno.NetMessageInvoke;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.netty.threadpool.Task;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.base.model.AccountBox;
import com.zl.server.play.base.model.AttrStorage;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.fight.model.Point;
import com.zl.server.play.fight.packet.*;
import com.zl.server.scene.FightScene;
import com.zl.server.scene.Scene;
import com.zl.server.scene.SceneManager;
import com.zl.server.scene.model.PlayerModel;
import com.zl.server.task.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
@Slf4j
public class FightSceneServiceImpl implements FightSceneService {

    @Autowired
    private TaskService taskService;

    @Storage
    private EntityCache<Integer, Account> accountEntityCache;

    public void createFightScene(Integer playerId, NetConnection netConnection, MS_CreateFightScene req) {
        FightScene fightScene = new FightScene(req.getDown(), req.getRight(), req.getMaxCount());
        Scene scene = SceneManager.putScene(fightScene);
        netConnection.sendMessage(new MR_Response("创建场景成功:" + scene.getSceneId()));
    }

    public void joinFightScene(Integer playerId, NetConnection netConnection, MS_JoinFightScene req) {
        Scene scene = SceneManager.getScene(req.getSceneId());
        if (scene == null) {
            netConnection.sendMessage(new MR_Response("当前场景不存在"));
            return;
        }
        if (!scene.canJoin()) {
            netConnection.sendMessage(new MR_Response("当前场景已满无法加入"));
            return;
        }
        //加入场景
        Boolean joining = netConnection.getAttr("joining", Boolean.class);
        if (joining != null && joining) {
            netConnection.sendMessage(new MR_Response("当前有场景正在加入中"));
            return;
        }
        netConnection.setAttr("scene_joining", true);
        //加入场景
        Task task = new Task(req.getSceneId(), () -> {
            joinFightScene(playerId, req.getSceneId());
        });
        CompletableFuture future = taskService.execSceneTask(task);
        future.whenCompleteAsync((resutl, err) -> {
            if (err != null) {
                log.error("err {}", err);
            }
            netConnection.setSceneId(req.getSceneId());
            netConnection.setAttr("scene_joining", false);
        });
    }

    public void joinFightScene(Integer playerId, Integer sceneId) {
        Account account = accountEntityCache.load(playerId);
        AccountBox model = account.getModel();
        PlayerModel playerModel = new PlayerModel();
        AttrStorage attrStorage = model.getAttrStorage();
        playerModel.setPlayerId(playerId);
        playerModel.setAttack(attrStorage.getAttack());
        playerModel.setDefense(attrStorage.getDefense());
        playerModel.setHp(attrStorage.getHp());
        playerModel.setPoint(new Point(0, 0));

        Scene scene = SceneManager.getScene(sceneId);
        scene.join(playerId, playerModel);
        SceneManager.addPlayerToScene(playerId, scene);
    }


    public void quitScene(Integer playerId, NetConnection netConnection) {
        netConnection.removeScene();
        SceneManager.quitScene(playerId);
        netConnection.sendMessage(new MR_Response("退出场景成功"));
    }


    public void movePoint(Integer playerId, NetConnection netConnection, MS_MovePoint req) {
        Scene scene = SceneManager.getSceneByPlayer(playerId);
        if (scene.movePoint(playerId, req.getPoint())) {
            netConnection.sendMessage(new MR_Response("移动坐标成功"));
            netConnection.sendMessage(new MR_MovePoint(req.getPoint()));
            return;
        }
        netConnection.sendMessage(new MR_Response("移动坐标失败"));
    }


    public void attack(Integer playerId, NetConnection netConnection, MS_Attack req) {
        Scene scene = SceneManager.getSceneByPlayer(playerId);
        if (scene == null) {
            netConnection.sendMessage(new MR_Response("当前场景不存在"));
            return;
        }
        FightScene fightScene = (FightScene) scene;
        fightScene.pk(playerId, req.getAttackedPlayerId());
    }

}
