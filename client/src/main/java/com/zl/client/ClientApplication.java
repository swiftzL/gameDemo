package com.zl.client;

import com.zl.client.client.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ConfigurableApplicationContext context = SpringApplication.run(ClientApplication.class, args);
        Client client = context.getBean(Client.class);
        client.run();
    }

}
