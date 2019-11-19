package com.lindaring.base.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "security.jwt")
public class SecurityProperties {
    private String secret = "secre";
    private String tokenPrefix = "Bearer ";
    private String headerString = "Authorization ";
    private long expirationTime = 864_000_000L;
}
