package com.zl.server.cache;

import com.alibaba.fastjson.JSON;
import com.github.benmanes.caffeine.cache.*;
import com.zl.server.cache.context.EntityManagerContext;
import com.zl.server.cache.persist.Persist;
import com.zl.server.commons.AbstractBlobModelEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityCache<PK, T> implements Cache<PK, T> {

    private Class<?> entityClass;
    private EntityManagerContext entityManagerContext;
    private LoadingCache<PK, T> cache;
    private Persist persist;
    private Class<?> cacheClass; //缓存model class

    //持久层
    public EntityCache(Class<?> entityClass, EntityManagerContext context, Persist persist, Class<?> cacheClass) {
        this.entityManagerContext = context;
        this.entityClass = entityClass;
        this.persist = persist;
        this.cache = Caffeine.newBuilder().writer(createCacheWriter()).build(createCacheLoader());
        this.cacheClass = cacheClass;
    }

    private CacheLoader<PK, T> createCacheLoader() {
        return pk -> {
            T entity = loadEntity(pk);
            return entity;
        };
    }

    private CacheWriter<PK, T> createCacheWriter() {
        return new CacheWriter<PK, T>() {
            @Override
            public void write(PK pk, T t) {
                persist.put(t);
            }

            @Override
            public void delete(PK pk, T t, RemovalCause removalCause) {
                return;
            }
        };
    }


    private T loadEntity(PK pk) {
        T o = (T) entityManagerContext.find(entityClass, pk);
        if (this.cacheClass != null) {
            AbstractBlobModelEntity abstractBlobModelEntity = (AbstractBlobModelEntity) o;
            String data = abstractBlobModelEntity.getData();
            abstractBlobModelEntity.setMode(JSON.parseObject(data, cacheClass));
        }
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

    public void writeBack(T t) {
        persist.put(t);
    }
}
