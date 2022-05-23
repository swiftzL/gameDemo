package com.zl.server.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Component
public class EntityManagerContext {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    public void persist(Object entity) {
        this.transactionTemplate.executeWithoutResult(transactionStatus -> {
            this.entityManager.merge(entity);
        });
    }

    public <T> T find(Class<T> clazz, Object pk) {
        return this.entityManager.find(clazz, pk);
    }


}
