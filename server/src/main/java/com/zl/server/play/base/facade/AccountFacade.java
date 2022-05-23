package com.zl.server.play.base.facade;

import com.zl.server.anno.NetMessageHandler;
import com.zl.server.anno.NetMessageInvoke;
import com.zl.server.commons.Command;
import com.zl.server.commons.Response;
import com.zl.server.dto.AccountDto;
import com.zl.server.netty.NetConnection;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.base.packet.MS_Account;
import com.zl.server.play.base.service.AccountService;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import sun.nio.ch.Net;

@NetMessageHandler
public class AccountFacade {

    @Autowired
    private AccountService accountService;

    @NetMessageInvoke(Command.Login)
    public MR_Response login(NetConnection netConnection, MS_Account ms_account) {
        return accountService.login(netConnection, ms_account);
    }

    @NetMessageInvoke(Command.AccountInfo)
    public MR_Response info(NetConnection netConnection) {
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
