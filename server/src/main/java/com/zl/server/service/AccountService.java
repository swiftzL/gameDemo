package com.zl.server.service;


import com.zl.server.anno.NetMessageHandler;
import com.zl.server.anno.NetMessageInvoke;
import com.zl.server.cache.EntityCache;
import com.zl.server.cache.Persist;
import com.zl.server.commons.Command;
import com.zl.server.commons.Response;
import com.zl.server.commons.Storage;
import com.zl.server.dao.AccountDao;
import com.zl.server.dto.AccountDto;
import com.zl.server.event.Event;
import com.zl.server.event.EventBroadCast;
import com.zl.server.model.Account;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@NetMessageHandler
public class AccountService {

    @Autowired
    AccountDao accountDao;
    @Autowired
    EventBroadCast eventBroadCast;


    EntityCache<Integer, Account> entityCache;

    @Autowired
    public void setEntityCache(EntityManager entityManager, Persist persist) {
        this.entityCache = new EntityCache<>(Account.class, entityManager, persist);
    }

    @NetMessageInvoke(Command.Login)
    public void login(AccountDto accountDto, Channel channel) {
        Account account = accountDao.findAccountByUsernameAndPassword(accountDto.getUsername(), accountDto.getPassword());
        if (account == null) {
            channel.writeAndFlush(Response.err("登录失败"));
            return;
        }
        Attribute<Integer> id = channel.attr(AttributeKey.valueOf("id"));
        id.set(account.getId());
        channel.writeAndFlush(Response.success("登录成功"));
    }

    @NetMessageInvoke(Command.AccountInfo)
    public void info(Channel channel) {
        AttributeKey<Integer> attributeInfo = AttributeKey.valueOf("id");
        Attribute<Integer> id = channel.attr(attributeInfo);
        if (id.get() == null) {
            channel.writeAndFlush(Response.err("当前用户未登录"));
            return;
        }
        Account account = entityCache.loadOrCreate(id.get());
        channel.writeAndFlush(Response.success("用户名:" + account.getUsername() + "-" + "等级:" + account.getLevel()));
    }

    @NetMessageInvoke(Command.CreateAccount)
    public void createAccount(AccountDto accountDto, Channel channel) {
        if (accountDao.existsAccountByUsername(accountDto.getUsername())) {
            channel.writeAndFlush(Response.err("该账号已存在"));
            return;
        }
        Account account = new Account();
        account.setUsername(accountDto.getUsername());
        account.setPassword(accountDto.getPassword());
        account.setLevel(1);
        accountDao.save(account);
        channel.writeAndFlush(Response.success("添加成功"));
    }

    @NetMessageInvoke(Command.Upgrade)
    public Response upgrade(Channel channel) {
        AttributeKey<Integer> attributeInfo = AttributeKey.valueOf("id");
        Attribute<Integer> id = channel.attr(attributeInfo);
        if (id.get() == null) {
            return Response.err("当前用户未登录");
        }
        Account account = entityCache.load(id.get());
        account.setLevel(account.getLevel() + 1);
        eventBroadCast.publish(Event.LEVEL, id.get());
        return Response.success("升级成功");
    }
}
