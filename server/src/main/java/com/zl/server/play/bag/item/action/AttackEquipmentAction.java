package com.zl.server.play.bag.item.action;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.netty.utils.NetMessageUtil;
import com.zl.server.play.bag.context.PropsContext;
import com.zl.server.play.bag.item.Item;
import com.zl.server.play.bag.item.ItemType;
import com.zl.server.play.bag.item.param.AttackParam;
import com.zl.server.play.bag.item.param.ExperienceDrugParam;
import com.zl.server.play.bag.packet.MR_Attack;
import com.zl.server.play.bag.resource.Attack;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.base.model.EquipmentStorage;
import com.zl.server.play.player.PlayerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttackEquipmentAction implements ItemAction {

    @Storage
    private EntityCache<Integer, Account> entityCache;

    @Autowired
    private PlayerContext playerContext;

    @Override
    public void action(int modelId, Integer playerId, int num, Item item) {
        AttackParam itemParam = PropsContext.getItemParam(modelId, AttackParam.class);
        int attack = itemParam.getAttack() * num;
        playerContext.addAttack(playerId, attack);
        MR_Attack mr_attack = new MR_Attack();
        mr_attack.setAttack(attack);
        Account account = entityCache.load(playerId);
        EquipmentStorage equipmentStorage = account.getModel().getEquipmentStorage();
        equipmentStorage.setWeapon(item);
        entityCache.writeBack(account);
        NetMessageUtil.sendMessage(playerId, mr_attack);
    }

    @Override
    public int getType() {
        return ItemType.Equipment_ATTACK.getCode();
    }
}