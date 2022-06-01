package com.zl.server.play.bag.item.action;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.netty.utils.NetMessageUtil;
import com.zl.server.play.bag.context.PropsContext;
import com.zl.server.play.bag.item.Item;
import com.zl.server.play.bag.item.ItemType;
import com.zl.server.play.bag.resource.param.AttackParam;
import com.zl.server.play.bag.service.BagService;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.equip.packet.MR_Attack;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.base.model.EquipmentStorage;
import com.zl.server.GameContext;
import com.zl.server.play.player.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttackEquipmentAction implements ItemAction {

    @Storage
    private EntityCache<Integer, Account> entityCache;

    @Override
    public void action(int modelId, Integer playerId, int num, Item item) {
        AttackParam itemParam = PropsContext.getItemParam(modelId, AttackParam.class);
        PlayerService playerService = GameContext.getPlayerService();
        Account account = entityCache.load(playerId);
        EquipmentStorage equipmentStorage = account.getModel().getEquipmentStorage();
        if (equipmentStorage.getWeapon() == null && num == -1) {
            NetMessageUtil.sendMessage(playerId, new MR_Response("当前装备不存在"));
            return;
        }
        int attack = itemParam.getAttack() * num;
        playerService.addAttack(playerId, attack);

        MR_Attack mr_attack = new MR_Attack();

        mr_attack.setAttack(attack);

        if (item != null && equipmentStorage.getWeapon() != null) {
            BagService bagService = GameContext.getBagService();
            bagService.addProps(playerId, item.getModelId(), 1);
        }
        equipmentStorage.setWeapon(item);

        entityCache.writeBack(account);
        NetMessageUtil.sendMessage(playerId, mr_attack);
    }

    @Override
    public int getType() {
        return ItemType.Equipment_ATTACK.getCode();
    }
}
