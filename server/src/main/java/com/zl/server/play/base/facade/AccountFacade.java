package com.zl.server.play.base.facade;

import com.zl.common.message.NetMessage;
import com.zl.server.netty.anno.NetMessageHandler;
import com.zl.server.netty.anno.NetMessageInvoke;
import com.zl.server.commons.Command;
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
    public MR_Response login(NetConnection netConnection, MS_Account ms_account) {
        return accountService.login(netConnection, ms_account);
    }

    @NetMessageInvoke(Command.AccountInfo)
    public NetMessage info(NetConnection netConnection) {
        return accountService.info(netConnection);
    }

    @NetMessageInvoke(Command.CreateAccount)
    public MR_Response createAccount(MS_Account ms_account, NetConnection netConnection) {
        return accountService.createAccount(ms_account, netConnection);
    }

    @NetMessageInvoke(Command.Upgrade)
    public MR_Response upgrade(NetConnection netConnection) {
        return accountService.upgrade(netConnection);
    }
}
