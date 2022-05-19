package com.zl.server.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class NettyServer {

    @Value("${gamedemo.port}")
    private int port;

    @Autowired
    @Qualifier("bossEventLoopGroup")
    private EventLoopGroup bossGroup;

    @Autowired
    @Qualifier("workEventLoopGroup")
    private EventLoopGroup workGroup;

    @Autowired
    private ChannelInitializer channelInitializer;

    private ServerBootstrap serverBootstrap;

    @PostConstruct
    public void init() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).childHandler(channelInitializer);
        this.serverBootstrap = serverBootstrap;
    }

    public void run() throws InterruptedException {
        serverBootstrap.bind(this.port).sync();
    }
}
