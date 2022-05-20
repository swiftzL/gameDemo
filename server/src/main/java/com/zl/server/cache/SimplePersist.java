package com.zl.server.cache;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class SimplePersist implements Persist {

    @PersistenceContext
    private EntityManager entityManager;

    private Map<Integer, Object> elements = new ConcurrentHashMap<>();
    private Lock lock = new ReentrantLock();
    private volatile boolean running = true;


    @Override
    public void destroy() {

    }

    @Override
    public Object get(Object id) {
        return elements.get((Integer) id);
    }

    @Override
    public void put(Object id, Object obj) {
        elements.put((Integer) id, obj);
    }



    @Transactional
    public void persist(Object obj) {
        this.entityManager.persist(obj);
    }

    @Override
    public void run() {

        while (running){
            lock.lock();
            elements.forEach((k, v) -> {
                persist(v);
                elements.remove(k);
            });

            lock.unlock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
