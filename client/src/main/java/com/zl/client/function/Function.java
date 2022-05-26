package com.zl.client.function;

import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public abstract class Function {
    @Autowired
    protected Scanner scanner;

    @Autowired
    protected Channel channel;


    public abstract int getCode();

    public abstract void run() throws ExecutionException, InterruptedException;

}
