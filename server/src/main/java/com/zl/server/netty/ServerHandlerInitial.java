package com.zl.server.netty;


import com.zl.server.codec.RequestDecoder;
import com.zl.server.codec.ResponseEncoder;
import com.zl.server.handler.ServiceHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerHandlerInitial extends ChannelInitializer<Channel> {

    @Autowired
    private RequestDecoder requestDecoder;
    @Autowired
    private ResponseEncoder responseEncoder;
    @Autowired
    private ServiceHandler serviceHandler;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(requestDecoder).addLast(responseEncoder).addLast(serviceHandler);
    }
}
