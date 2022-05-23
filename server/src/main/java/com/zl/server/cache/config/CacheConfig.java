package com.zl.server.cache.config;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.EntityManagerContext;
import com.zl.server.cache.Persist;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.quest.model.Quest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    @Qualifier("taskEntityCache")
    public EntityCache taskEntityCache(EntityManagerContext context, Persist persist) {
        return new EntityCache<>(Quest.class, context, persist);
    }

    @Bean
    @Qualifier("accountEntityCache")
    public  EntityCache accountEntityCache(EntityManagerContext context,Persist persist){
        return new EntityCache(Account.class,context,persist);
    }
}
