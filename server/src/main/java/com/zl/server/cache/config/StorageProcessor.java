package com.zl.server.cache.config;


import com.zl.server.cache.Cache;
import com.zl.server.cache.EntityCache;
import com.zl.server.cache.context.EntityManagerContext;
import com.zl.server.cache.persist.Persist;
import com.zl.server.cache.anno.Storage;
import com.zl.server.commons.AbstractBlobModelEntity;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.ReflectionUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class StorageProcessor implements BeanPostProcessor, Ordered {
    @Autowired
    private EntityManagerContext entityManagerContext;

    @Autowired
    private Persist persist;

    private Map<Class<?>, Cache> cacheMap = new HashMap<>();


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), field -> {
            Storage storage = field.getAnnotation(Storage.class);
            if (storage != null) {
                Class<?> type = field.getType();
                if (Cache.class.isAssignableFrom(type)) {
                    inject(bean, field);
                }
            }
        });
        return bean;
    }

    /**
     * 注入缓存字段
     *
     * @param bean
     * @param field
     */
    public void inject(Object bean, Field field) {
        Class<?> entityClass = getEntityType(field);
        Cache cache = getOrCreate(entityClass);
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, bean, cache);
    }

    public Cache getOrCreate(Class<?> entityClass) {
        Cache cache = cacheMap.get(entityClass);
        if (cache != null) {
            return cache;
        }
        Class<?> cacheClass = getCacheClass(entityClass);
        cache = new EntityCache<>(entityClass, entityManagerContext, persist, cacheClass);
        cacheMap.putIfAbsent(entityClass, cache);
        return cache;

    }

    /**
     * 获取缓存容器class
     * @param entityClass
     * @return
     */
    public Class<?> getCacheClass(Class<?> entityClass) {
        if (!AbstractBlobModelEntity.class.isAssignableFrom(entityClass)) {
            return null;
        }
        Type type = entityClass.getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedTypeImpl) type).getActualTypeArguments();
        return (Class<?>) actualTypeArguments[0];
    }

    /**
     * 获取entity class类型
     * @param field
     * @return
     */
    public Class<?> getEntityType(Field field) {
        Type genericType = field.getGenericType();
        Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
        return (Class) actualTypeArguments[1];
    }


    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
