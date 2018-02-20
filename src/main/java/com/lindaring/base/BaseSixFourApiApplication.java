package com.lindaring.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BaseSixFourApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseSixFourApiApplication.class, args);
    }

}
