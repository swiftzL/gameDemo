package com.zl.server.play.base.service;

import com.zl.common.message.NetMessage;
import com.zl.server.netty.connection.NetConnection;
import com.zl.server.play.base.packet.MR_Response;
import com.zl.server.play.base.packet.MS_Account;


public interface AccountService {

    void login(NetConnection netConnection, MS_Account ms_account);

    void info(Integer playerId, NetConnection netConnection);

    void createAccount(MS_Account ms_account, NetConnection netConnection);

    void upgrade(Integer playerId, NetConnection netConnection);

    void logout(Integer playerId,NetConnection netConnection);
}
