package com.zl.server.netty.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
public class ServerConfig {
    private String host = "127.0.0.1";
    private Integer port = 8088;
}
