package com.zl.server.netty;


import com.zl.server.netty.codec.RequestDecoder;
import com.zl.server.netty.codec.ResponseEncoder;
import com.zl.server.netty.config.ServerConfig;
import com.zl.server.netty.handler.ServiceHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class NettyServer {
    @Autowired
    private ServerConfig serverConfig;

    private ServerBootstrap serverBootstrap;

    @PostConstruct
    public void init() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        serverBootstrap.group(new NioEventLoopGroup(1), new NioEventLoopGroup()).channel(NioServerSocketChannel.class).childHandler(new ServerHandlerInitial());
        this.serverBootstrap = serverBootstrap;
    }

    public void run() throws InterruptedException {
        serverBootstrap.bind(serverConfig.getHost(), serverConfig.getPort()).sync();
    }


    static class ServerHandlerInitial extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel ch) throws Exception {
            ch.pipeline().addLast(new RequestDecoder()).addLast(new ResponseEncoder()).addLast(new ServiceHandler());
        }
    }
}
