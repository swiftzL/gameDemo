package com.zl.client;

import com.zl.client.client.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(ClientApplication.class, args);
        Client client = context.getBean(Client.class);
        client.printFunction();
    }

}
