package com.zl.client.client;

import com.zl.common.common.Command;

import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Client {

    @Autowired
    private Channel channel;

    public void printFunction() {
        for (Command command : Command.values()) {
            System.out.println(command.getCode() + "," + command.getDesc());
        }
        System.out.println("100:退出");
    }


    public void run() {
        printFunction();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int code = scanner.nextInt();
            if (code == 100) {
                return;
            }

        }

    }


}
