package com.zl.server.cache.persist;

import com.alibaba.fastjson.JSON;
import com.zl.server.cache.config.CacheConfig;
import com.zl.server.cache.context.EntityManagerContext;
import com.zl.server.commons.AbstractBlobModelEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Deque;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
public class SimplePersist implements Persist {

    @Autowired
    private EntityManagerContext entityManagerContext;

    @Autowired
    private CacheConfig cacheConfig;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private Deque<Object> elements = new ConcurrentLinkedDeque<>();
    private Lock lock = new ReentrantLock();
    private volatile boolean running = true;


    @Override
    public void destroy() {
        this.running = false;
        for (Object obj : elements) {
            persist(obj);
        }
        executorService.shutdown();
    }

    @Override
    public void put(Object obj) {
        elements.addLast(obj);
    }

    public void persist(Object obj) {
        log.info("persisting");
        if (obj instanceof AbstractBlobModelEntity) {
            AbstractBlobModelEntity entity = (AbstractBlobModelEntity) obj;
            entity.setData(JSON.toJSONString(entity.getModel()));
        }
        this.entityManagerContext.persist(obj);
        log.info("persisted");
    }

    @Override
    public void run() {
        if (running && !elements.isEmpty()) {
            for (int i = 0; i < this.cacheConfig.getPersistNum(); i++) {
                if (elements.isEmpty()) {
                    return;
                }
                Object obj = elements.removeFirst();
                log.info("persist {}", obj);
                try {
                    persist(obj);
                } catch (Exception e) {
                    log.error("exception ", e);
                }
            }
        }
    }

    @PostConstruct
    public void init() {
        executorService.scheduleWithFixedDelay(this, 15, 15, TimeUnit.SECONDS);
        log.info("Persist started");
    }


}
