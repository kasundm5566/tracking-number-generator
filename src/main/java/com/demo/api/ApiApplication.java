package com.demo.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class ApiApplication {

    private static final Logger logger = LoggerFactory.getLogger(ApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Scheduled(fixedRate = 60000)
    public void logHeartbeat() {
        logger.info("The application is running...");
    }
}