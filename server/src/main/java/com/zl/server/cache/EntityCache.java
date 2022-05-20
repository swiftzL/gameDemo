package com.zl.server.cache;

import com.github.benmanes.caffeine.cache.*;
import lombok.Data;

import javax.persistence.EntityManager;

@Data
public class EntityCache<PK, T> implements Cache<PK, T> {

    private Class<?> entityClass;
    private EntityManager entityManager;
    private LoadingCache<PK, T> cache;
    private Persist persist;

    //持久层
    public EntityCache(Class<?> entityClass, EntityManager entityManager, Persist persist) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
        this.persist = persist;
        this.cache = Caffeine.newBuilder().writer(createCacheWriter()).build(createCacheLoader());
    }

    private CacheLoader<PK, T> createCacheLoader() {
        return new CacheLoader<PK, T>() {
            @Override
            public T load(PK pk) throws Exception {
                T entity = loadEntity(pk);
                return entity;
            }
        };
    }

    private CacheWriter<PK, T> createCacheWriter() {
        return new CacheWriter<PK, T>() {
            @Override
            public void write(PK pk, T t) {
                persist.put(pk, t);
            }

            @Override
            public void delete(PK pk, T t, RemovalCause removalCause) {
                return;
            }
        };
    }


    private T loadEntity(PK pk) {
        T o = (T) entityManager.find(entityClass, pk);
        return o;
    }


    @Override
    public T load(PK id) {
        return cache.get(id);
    }


    @Override
    public T loadOrCreate(PK id) {
        T t = cache.get(id);
        if (t != null) {
            return t;
        }
        return cache.asMap().compute(id, (pk, entity) -> {
            if (entity == null) {
                entity = loadEntity(pk);
            }
            return entity;
        });
    }

    @Override
    public void update(PK id, T t) {
        cache.put(id, t);
    }

    public void writeBack(PK pk, T t) {
        persist.put(pk, t);
    }


}
