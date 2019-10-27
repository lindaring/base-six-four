package com.lindaring.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class Base64ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Base64ApiApplication.class, args);
    }

}
