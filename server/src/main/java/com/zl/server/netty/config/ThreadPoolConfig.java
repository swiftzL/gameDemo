package com.zl.server.netty.config;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ThreadPoolConfig {

    @Bean
    @Qualifier("bossEventLoopGroup")
    public EventLoopGroup bossEventLoopGroup(){
        return new NioEventLoopGroup(1);
    }

    @Bean
    @Qualifier("workEventLoopGroup")
    public EventLoopGroup workEventLoopGroup(){
        return new NioEventLoopGroup();
    }

}
