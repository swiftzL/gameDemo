package com.zl.server.play.base.service;

import com.zl.server.anno.NetMessageInvoke;
import com.zl.server.cache.EntityCache;
import com.zl.server.commons.Command;
import com.zl.server.commons.Response;
import com.zl.server.dao.AccountDao;
import com.zl.server.dto.AccountDto;

import com.zl.server.model.Account;
import com.zl.server.netty.NetConnection;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.base.packet.MS_Account;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountDao accountDao;


    @Autowired
    @Qualifier("accountEntityCache")
    EntityCache<Integer, Account> entityCache;

    @NetMessageInvoke(Command.Login)
    public MR_Response login(NetConnection netConnection, MS_Account ms_account) {
        Account account = accountDao.findAccountByUsernameAndPassword(ms_account.getUsername(), ms_account.getPassword());
        if (account == null) {
            return new MR_Response("登录失败");
        }
        netConnection.setAttr("id", account.getId());
        return new MR_Response("登录成功");
    }

    @NetMessageInvoke(Command.AccountInfo)
    public MR_Response info(NetConnection netConnection) {
        AttributeKey<Integer> attributeInfo = AttributeKey.valueOf("id");
        Integer id = netConnection.getAttr("id", Integer.class);
        if (id == null) {
            return new MR_Response("当前用户未登录");
        }
        Account account = entityCache.loadOrCreate(id);
        return new MR_Response("用户名:" + account.getUsername() + "-" + "等级:" + account.getLevel());
    }

    @NetMessageInvoke(Command.CreateAccount)
    public MR_Response createAccount(MS_Account ms_account, NetConnection netConnection) {
        if (accountDao.existsAccountByUsername(ms_account.getUsername())) {
            return new MR_Response("当前用户已存在");
        }
        Account account = new Account();
        account.setUsername(ms_account.getUsername());
        account.setPassword(ms_account.getPassword());
        account.setLevel(1);
        accountDao.save(account);
        return new MR_Response("创建用户成功");
    }

    @NetMessageInvoke(Command.Upgrade)
    public MR_Response upgrade(NetConnection netConnection) {
        Integer id = netConnection.getAttr("id", Integer.class);
        if (id == null) {
            return new MR_Response("当前用户未登录");
        }
        Account account = entityCache.load(id);
        account.setLevel(account.getLevel() + 1);
//        eventBroadCast.publish(Event.LEVEL, id.get());
        entityCache.writeBack(account);
        return new MR_Response("升级成功");
    }
}