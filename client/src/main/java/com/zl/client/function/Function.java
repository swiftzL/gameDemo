package com.zl.client.function;

import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

public abstract class Function implements Runnable, FunctionAware {
    @Autowired
    Scanner scanner;

    @Autowired
    Channel channel;


    abstract int getCode();

}
