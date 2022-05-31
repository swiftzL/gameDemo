package com.zl.server.netty.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
public class ServerConfig {
    @Value("${gamedemo.host}")
    private String host;
    @Value("${gamedemo.port}")
    private Integer port;
}
