package com.zl.server.netty;


import com.zl.server.netty.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ServerRunner implements CommandLineRunner {

    @Autowired
    private NettyServer nettyServer;

    @Override
    public void run(String... args) throws Exception {
        nettyServer.run();
    }
}
