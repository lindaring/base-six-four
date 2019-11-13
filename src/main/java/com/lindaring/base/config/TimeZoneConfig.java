package com.lindaring.base.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
@Configuration
public class TimeZoneConfig {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Africa/Johannesburg"));
        log.info("Date in UTC: " + new Date().toString());
    }

}
