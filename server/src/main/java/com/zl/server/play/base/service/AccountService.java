package com.zl.server.play.base.service;


import com.zl.server.anno.NetMessageHandler;
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


public interface AccountService {

    MR_Response login(NetConnection netConnection, MS_Account ms_account);
    MR_Response info(NetConnection netConnection);
    MR_Response createAccount(MS_Account ms_account, NetConnection netConnection);
    MR_Response upgrade(NetConnection netConnection);
}
