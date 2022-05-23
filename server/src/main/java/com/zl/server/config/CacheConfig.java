package com.zl.server.config;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.EntityManagerContext;
import com.zl.server.cache.Persist;
import com.zl.server.model.Account;
import com.zl.server.model.Task;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class CacheConfig {

    @Bean
    @Qualifier("taskEntityCache")
    public EntityCache taskEntityCache(EntityManagerContext context, Persist persist) {
        return new EntityCache<>(Task.class, context, persist);
    }

    @Bean
    @Qualifier("accountEntityCache")
    public  EntityCache accountEntityCache(EntityManagerContext context,Persist persist){
        return new EntityCache(Account.class,context,persist);
    }
}
