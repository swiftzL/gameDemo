package com.zl.server.cache;

import com.alibaba.fastjson.JSON;
import com.zl.server.commons.AbstractBlobModelEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.Deque;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
public class SimplePersist implements Persist {

    @Autowired
    private EntityManagerContext entityManagerContext;
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private Deque<Object> elements = new ConcurrentLinkedDeque<>();
    private Lock lock = new ReentrantLock();
    private volatile boolean running = true;


    @Override
    public void destroy() {
        this.running = false;
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
            lock.lock();
            Object obj = elements.removeFirst();
            log.info("persist {}", obj);
            lock.unlock();
            try {
                persist(obj);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @PostConstruct
    public void init() {
        executorService.scheduleWithFixedDelay(this, 15, 15, TimeUnit.SECONDS);
        log.info("Persist started");
    }


}
