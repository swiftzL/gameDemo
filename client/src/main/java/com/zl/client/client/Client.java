package com.zl.client.client;

import com.alibaba.fastjson.JSON;
import com.zl.client.common.Request;
import com.zl.client.function.Function;
import com.zl.common.common.Command;

import com.zl.common.model.AccountDto;
import io.netty.channel.Channel;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Attributes;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
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
    private List<Function> functions;

    @Autowired
    private LineReader lineReader;

    @PostConstruct
    public void init() {
        this.functions.sort((e1, e2) -> e1.getCode() - e2.getCode());
    }

    public void printFunction() {
        for (Command command : Command.values()) {
            System.out.println(command.getCode() + "," + command.getDesc());
        }
        System.out.println(this.functions.size() + 1 + ":功能列表");
    }


    public void run() throws ExecutionException, InterruptedException, IOException {
        printFunction();
        while (true) {
            String line = lineReader.readLine("gameDemo>"); // 获取输入的信息
            int code = 0;
            try {
                code = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            try {
                functions.get(code - 1).run();
            } catch (Exception e) {
                e.printStackTrace();
                printFunction();
            }
            if (code == 18) {
                System.exit(0);
            }
        }
    }


}
