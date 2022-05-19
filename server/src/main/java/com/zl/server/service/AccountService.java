package com.zl.server.service;


import com.zl.server.anno.NetMessageHandler;
import com.zl.server.anno.NetMessageInvoke;
import com.zl.server.commons.Command;
import com.zl.server.dto.AccountDto;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

@NetMessageHandler
public class AccountService {

    @NetMessageInvoke(Command.Login)
    public void login(AccountDto accountDto, Channel channel){

    }

    @NetMessageInvoke(Command.AccountInfo)
    public void info(Channel channel){
        AttributeKey<Object> attributeInfo = AttributeKey.valueOf("info");
    }

    @NetMessageInvoke(Command.CreateAccount)
    public void createAccount(Channel channel){

    }
}
