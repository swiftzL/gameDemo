package com.zl.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ScannerConfig {

    @Bean
    public Scanner scanner(){
        return new Scanner(System.in);
    }

}
