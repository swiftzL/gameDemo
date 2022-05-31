package com.zl.server.play.quest.service;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.commons.Constants;
import com.zl.server.netty.utils.NetMessageUtil;
import com.zl.server.play.bag.model.Bag;
import com.zl.server.play.player.PlayerContext;
import com.zl.server.play.quest.config.AwardManager;
import com.zl.server.play.quest.config.QuestManager;
import com.zl.server.play.quest.config.QuestResourceConfig;
import com.zl.server.play.quest.event.QuestEvent;
import com.zl.server.play.quest.model.Quest;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.quest.model.QuestBox;
import com.zl.server.play.quest.model.QuestStorage;
import com.zl.server.play.quest.packet.MR_Quests;
import com.zl.server.play.quest.packet.MS_Quest;
import com.zl.server.play.quest.resource.Award;
import com.zl.server.play.quest.resource.QuestConfig;
import com.zl.server.play.quest.resource.QuestProcessor;
import com.zl.server.play.quest.resource.QuestResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class QuestServiceImpl implements QuestService {

    @Storage
    private EntityCache<Integer, Quest> questEntityCache;
    @Storage
    private EntityCache<Integer, Bag> bagEntityCache;
    private Map<Integer, QuestProcessor> questProcessorMap;
    private Map<Integer, List<QuestBox>> idToQuest = new HashMap<>();
    private Map<Integer, QuestResource> questConfigMap = QuestResourceConfig.allQuestConfigMap;

    @Autowired
    public void setQuestProcessorMap(Set<QuestProcessor> questProcessors) {
        this.questProcessorMap = new HashMap<>();
        for (QuestProcessor questProcessor : questProcessors) {
            this.questProcessorMap.put(questProcessor.getQuestType().getCode(), questProcessor);
        }
    }

    public void showTask(Integer playerId, NetConnection netConnection) {
        Quest quest = questEntityCache.loadOrCreate(playerId);
        List<QuestStorage> questStorages = quest.getModel().getQuestStorages();
        MR_Quests packet = new MR_Quests();
        packet.setQuestStorages(questStorages);
        netConnection.sendMessage(packet);

    }

    public void handleQuestEvent(QuestEvent questEvent) {
        Integer playerId = questEvent.getPlayerId();
        Quest quest = questEntityCache.loadOrCreate(playerId);
        for (QuestStorage questStorage : quest.getModel().getQuestStorages()) {
            if (questStorage.getTaskStatus().equals(1)) {//跳过完成的任务
                continue;
            }
            QuestProcessor questProcessor = getQuestProcessor(questStorage.getTaskType());
            QuestConfig questConfig = questProcessor.getQuestConfig(questStorage.getTaskId());
            if (questConfig != null && questConfig.getFinishCondition().getOperationType() == questEvent.getType()) {
                questProcessor.finish(playerId, questStorage.getTaskId(), quest, questStorage, questEvent.getParams());
            }
        }
    }

    //校验背包

    //领取
    public void drawAward(Integer playerId, MS_Quest req) throws Exception {
        Quest quest = questEntityCache.loadOrCreate(playerId);
        QuestBox questBox = quest.getModel();
        List<QuestStorage> questStorages = questBox.getQuestStorages();
        if (PlayerContext.INSTANCE.bagIsFull(playerId)) {
            NetMessageUtil.sendMessage(playerId, new MR_Response("背包容量满了"));
            return;
        }
        for (QuestStorage questStorage : questStorages) {
            if (questStorage.getTaskId().equals(req.getQuestId())) {
                if (questStorage.getIsReceive().equals(Constants.YES)) {
                    NetMessageUtil.sendMessage(playerId, new MR_Response("当前奖励已经领取"));
                    return;
                }
                //修改状态
                questStorage.setIsReceive(Constants.YES);
                questEntityCache.writeBack(quest);
                //添加背包
                Award award = AwardManager.awardMap.get(req.getQuestId());
                if (PlayerContext.INSTANCE.addProps(playerId, award.getModeId(), award.getNum())) {
                    NetMessageUtil.sendMessage(playerId, new MR_Response("领取奖励成功"));
                } else {
                    NetMessageUtil.sendMessage(playerId, new MR_Response("领取奖励失败-背包容量不够"));
                }
            }
        }
        NetMessageUtil.sendMessage(playerId, new MR_Response("当前任务不存在"));
    }

    //接受任务
    public void acceptQuest(Integer playerId, MS_Quest req) {
        Quest quest = questEntityCache.loadOrCreate(playerId);
        List<QuestStorage> questStorages = quest.getModel().getQuestStorages();
        if (existQuest(req.getQuestId(), questStorages)) {
            NetMessageUtil.sendMessage(playerId, new MR_Response("当前任务已存在"));
            return;
        }

        QuestStorage questStorage = createQuestStorage(playerId, req.getQuestId());
        questStorages.add(questStorage);
        questEntityCache.writeBack(quest);
        NetMessageUtil.sendMessage(playerId, new MR_Response("领取任务成功"));
    }

    //获取任务
    private QuestStorage createQuestStorage(Integer playerId, Integer questId) {
        QuestResource questResource = questConfigMap.get(questId);
        QuestStorage questStorage = new QuestStorage();
        questStorage.setTaskName(questResource.getQuestName());
        questStorage.setTaskStatus(Constants.NO);
        questStorage.setTaskId(questResource.getId());
        questStorage.setCurrent(questResource.getQuestCondition().getCurrent(playerId));
        questStorage.setMaxCount(questResource.getQuestCondition().getMaxCount());
        questStorage.setTaskType(questResource.getType());
        questStorage.setIsReceive(Constants.NO);
        return questStorage;
    }

    private boolean existQuest(Integer questId, List<QuestStorage> questStorages) {
        if (questStorages == null) {
            return false;
        }
        for (QuestStorage questStorage1 : questStorages) {
            if (questStorage1.getTaskId().equals(questId)) {
                return true;
            }
        }
        return false;
    }

    private QuestProcessor getQuestProcessor(Integer questType) {
        return questProcessorMap.get(questType);
    }
}
