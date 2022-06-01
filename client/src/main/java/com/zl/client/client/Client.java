package com.zl.client.client;

import com.alibaba.fastjson.JSON;
import com.zl.client.common.Request;
import com.zl.client.function.Function;
import com.zl.common.common.Command;

import com.zl.common.model.AccountDto;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

@Component
public class Client {

    @Autowired
    private Channel channel;

    @Autowired
    private List<Function>  functions;

    @Autowired
    private Scanner scanner;

    @PostConstruct
    public void init(){
        this.functions.sort((e1,e2)->e1.getCode()-e2.getCode());
    }

    public void printFunction() {
        for (Command command : Command.values()) {
            System.out.println(command.getCode() + "," + command.getDesc());
        }
        System.out.println("100:退出");
    }


    public void run() throws ExecutionException, InterruptedException {
        printFunction();

        while (scanner.hasNext()) {
            int code = scanner.nextInt();
            scanner.nextLine();
            if(code==13){
                printFunction();
                continue;
            }
            functions.get(code-1).run();
            if (code == 100) {
                return;
            }
        }

    }


}
