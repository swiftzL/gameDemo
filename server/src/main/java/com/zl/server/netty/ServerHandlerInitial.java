package com.zl.server.netty;


import com.zl.server.codec.RequestDecoder;
import com.zl.server.codec.ResponseEncoder;
import com.zl.server.handler.ServiceHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class ServerHandlerInitial extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(new RequestDecoder()).addLast(new ResponseEncoder()).addLast(new ServiceHandler());
    }
}
