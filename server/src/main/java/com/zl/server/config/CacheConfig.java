package com.zl.server.config;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.Persist;
import com.zl.server.model.Task;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class CacheConfig {

    @Bean
    @Qualifier("taskEntityCache")
    public EntityCache taskEntityCache(EntityManager entityManager, Persist persist) {
        return new EntityCache<>(Task.class, entityManager, persist);
    }
}
