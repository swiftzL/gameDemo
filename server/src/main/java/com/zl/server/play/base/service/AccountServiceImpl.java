package com.zl.server.play.base.service;

import com.zl.server.cache.anno.Storage;
import com.zl.server.cache.EntityCache;
import com.zl.server.netty.utils.NetMessageUtil;
import com.zl.server.play.base.dao.AccountDao;


import com.zl.server.netty.connection.NetConnection;
import com.zl.server.play.base.event.LoginEvent;
import com.zl.server.play.base.event.UpgradeEvent;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.base.model.AttrStorage;
import com.zl.server.play.base.model.EquipmentStorage;
import com.zl.server.play.base.packet.MR_AccountInfo;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.base.packet.MS_Account;
import com.zl.server.play.base.packet.vo.AccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ApplicationContext applicationContext;
    @Storage
    EntityCache<Integer, Account> entityCache;

    public void login(NetConnection netConnection, MS_Account req) {
        Account account = accountDao.findAccountByUsernameAndPassword(req.getUsername(), req.getPassword());
        if (account == null) {
            netConnection.sendMessage(new MR_Response("登录失败"));
            return;
        }
        netConnection.setAttr("id", account.getId());

        NetMessageUtil.addConnection(account.getId(), netConnection);
        applicationContext.publishEvent(LoginEvent.valueOf(account.getId(), this, null));
        netConnection.sendMessage(new MR_Response("登录成功"));
    }

    public void info(Integer playerId, NetConnection netConnection) {
        Account account = entityCache.loadOrCreate(playerId);

        MR_AccountInfo packet = new MR_AccountInfo();
        packet.setUsername(account.getUsername());
        packet.setLevel(account.getLevel());

        //set vo
        AccountVo accountVo = new AccountVo();
        AttrStorage attrStorage = account.getModel().getAttrStorage();
        accountVo.setAttack(attrStorage.getAttack());
        accountVo.setDefense(attrStorage.getDefense());

        EquipmentStorage equipmentStorage = account.getModel().getEquipmentStorage();
        accountVo.setCloth(equipmentStorage.getCloth());
        accountVo.setShoe(equipmentStorage.getShoe());
        accountVo.setWeapon(equipmentStorage.getWeapon());

        packet.setAccountVo(accountVo);
        netConnection.sendMessage(packet);
    }

    public void createAccount(MS_Account req, NetConnection netConnection) {
        if (accountDao.existsAccountByUsername(req.getUsername())) {
            netConnection.sendMessage(new MR_Response("当前用户已存在"));
            return;
        }
        Account account = new Account();
        account.setUsername(req.getUsername());
        account.setPassword(req.getPassword());

        accountDao.save(account);

        netConnection.sendMessage(new MR_Response("创建用户成功"));
    }

    public void upgrade(Integer playerId, NetConnection netConnection) {
        Account account = entityCache.load(playerId);
        account.setLevel(account.getLevel() + 1);
        applicationContext.publishEvent(UpgradeEvent.valueOf(playerId, this, null));
        entityCache.writeBack(account);
        netConnection.sendMessage(new MR_Response("升级完成"));
    }
}
