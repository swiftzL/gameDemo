package com.zl.server.cache;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Component
public class EntityManagerContext {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Transactional
    public void persist(Object entity) {
        this.entityManager.merge(entity);
//        this.entityManager.persist(entity);
    }

    public <T> T find(Class<T> clazz, Object pk) {
        return this.entityManager.find(clazz, pk);
    }


}
