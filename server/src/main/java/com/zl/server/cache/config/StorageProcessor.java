package com.zl.server.cache.config;


import com.zl.server.cache.Cache;
import com.zl.server.cache.EntityCache;
import com.zl.server.cache.EntityManagerContext;
import com.zl.server.cache.Persist;
import com.zl.server.cache.anno.Storage;
import com.zl.server.commons.AbstractBlobModelEntity;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.ReflectionUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class StorageProcessor implements BeanPostProcessor, ApplicationContextAware, Ordered {

    private ApplicationContext applicationContext;
    private Map<Class<?>, Cache> cacheMap = new HashMap<>();
    @Autowired
    private EntityManagerContext entityManagerContext;
    @Autowired
    private Persist persist;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

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

    public Class<?> getCacheClass(Class<?> entityClass) {
        if (!AbstractBlobModelEntity.class.isAssignableFrom(entityClass)) {
            return null;
        }
        Type type = entityClass.getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedTypeImpl) type).getActualTypeArguments();
        return (Class<?>) actualTypeArguments[0];
    }

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
