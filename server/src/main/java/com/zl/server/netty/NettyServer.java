package com.zl.server.netty;


import com.zl.server.netty.config.ServerConfig;
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
    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    @Qualifier("bossEventLoopGroup")
    private EventLoopGroup bossGroup;

    @Autowired
    @Qualifier("workEventLoopGroup")
    private EventLoopGroup workGroup;


    private ServerBootstrap serverBootstrap;

    @PostConstruct
    public void init() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).childHandler(new ServerHandlerInitial());
        this.serverBootstrap = serverBootstrap;
    }

    public void run() throws InterruptedException {
        serverBootstrap.bind(serverConfig.getHost(), serverConfig.getPort()).sync();
    }
}
