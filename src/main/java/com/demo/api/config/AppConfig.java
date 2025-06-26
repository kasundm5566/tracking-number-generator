package com.demo.api.config;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(name = "customCorrelationIdFilter")
    public Filter correlationIdFilter() {
        return new CorrelationIdFilter();
    }
}
