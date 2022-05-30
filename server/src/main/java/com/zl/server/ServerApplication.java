package com.zl.server;

import com.zl.server.cache.context.EntityManagerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication  {

    @Autowired
    private EntityManagerContext entityManagerContext;


    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
