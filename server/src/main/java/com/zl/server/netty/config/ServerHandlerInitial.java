package com.zl.server.netty.config;


import com.zl.server.netty.codec.RequestDecoder;
import com.zl.server.netty.codec.ResponseEncoder;

import com.zl.server.netty.handler.ServiceHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;


public class ServerHandlerInitial extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(new RequestDecoder()).addLast(new ResponseEncoder()).addLast(new ServiceHandler());
    }
}
