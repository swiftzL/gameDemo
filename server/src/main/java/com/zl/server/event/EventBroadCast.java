package com.zl.server.event;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;


@Component
public class EventBroadCast {

    @Qualifier("asyncPoolExecutor")
    @Autowired
    private  Executor executor;


    public void publish(Event event){
        executor.execute(()->{

        });
    }

}
