package com.zl.server;

import com.zl.server.cache.context.EntityManagerContext;
import com.zl.server.netty.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class ServerApplication  {
    
    public static void main(String[] args) throws InterruptedException {
        NettyServer nettyServer = SpringApplication.run(ServerApplication.class, args).getBean(NettyServer.class);
        nettyServer.run();
    }

}
