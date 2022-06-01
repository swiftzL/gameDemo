package com.zl.server.cache.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "gamedemo.cache")
@Configuration
@Getter
@Setter
public class CacheConfig {
    private int persistNum;
}
