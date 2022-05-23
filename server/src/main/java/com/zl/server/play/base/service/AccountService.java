package com.zl.server.play.base.service;

import com.zl.server.netty.connection.NetConnection;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.base.packet.MS_Account;


public interface AccountService {

    MR_Response login(NetConnection netConnection, MS_Account ms_account);
    MR_Response info(NetConnection netConnection);
    MR_Response createAccount(MS_Account ms_account, NetConnection netConnection);
    MR_Response upgrade(NetConnection netConnection);
}
