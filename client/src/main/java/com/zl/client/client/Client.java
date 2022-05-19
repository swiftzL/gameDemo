package com.zl.client.client;

import com.alibaba.fastjson.JSON;
import com.zl.client.common.Request;
import com.zl.common.common.Command;

import com.zl.common.model.AccountDto;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;

@Component
public class Client {

    @Autowired
    private Channel channel;

    private Map<Integer, Runnable> functions = new HashMap<>();
    private  Scanner scanner = new Scanner(System.in);
    private AtomicInteger atomicInteger = new AtomicInteger();

    @PostConstruct
    public void init(){
        functions.put(1,()->{

        });

        functions.put(2,()->{

        });


    }

    public void printFunction() {
        for (Command command : Command.values()) {
            System.out.println(command.getCode() + "," + command.getDesc());
        }
        System.out.println("100:退出");
    }


    public void run() {
        printFunction();

        while (scanner.hasNext()) {
            int code = scanner.nextInt();
            if (code == 100) {
                return;
            }


        }

    }


}
