package com.zl.server.cache;

import lombok.Data;

import javax.persistence.EntityManager;

@Data
public class EntityCache<PK,T> implements Cache<PK,T> {

    private LocalCache localCache = LocalCache.Instance;
    private Class<?> entityClass;
    private EntityManager entityManager;




    @Override
    public T load(PK id) {
        return (T)localCache.get(id);
    }

    @Override
    public T loadOrCreate(PK id) {
        Object obj = localCache.get(id);
        if(obj==null){
            obj = entityManager.find(entityClass, id);
        }
        localCache.put(id,obj);
        return (T)obj;
    }


}
