package com.zl.server.play.base.facade;

import com.zl.common.message.NetMessage;
import com.zl.server.netty.anno.NetMessageHandler;
import com.zl.server.netty.anno.NetMessageInvoke;
import com.zl.server.commons.Command;
import com.zl.server.netty.anno.Param;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.base.packet.MS_Account;
import com.zl.server.play.base.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

@NetMessageHandler
public class AccountFacade {

    @Autowired
    private AccountService accountService;

    @NetMessageInvoke(Command.Login)
    public void login(NetConnection netConnection, MS_Account ms_account) {
        accountService.login(netConnection, ms_account);
    }

    @NetMessageInvoke(Command.AccountInfo)
    public void info(@Param("id") Integer playerId, NetConnection netConnection) {
        accountService.info(playerId, netConnection);
    }

    @NetMessageInvoke(Command.CreateAccount)
    public void createAccount(MS_Account ms_account, NetConnection netConnection) {
        accountService.createAccount(ms_account, netConnection);
    }

    @NetMessageInvoke(Command.Upgrade)
    public void upgrade(@Param("id") Integer playerId, NetConnection netConnection) {
        accountService.upgrade(playerId, netConnection);
    }

    @NetMessageInvoke(Command.LOGOUT)
    public void logout(@Param("id") Integer playerId, NetConnection netConnection) {
        accountService.logout(playerId, netConnection);
    }
}
