package com.lindaring.base.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "api.mail")
public class MailProperties {
  private boolean enabled;
  private String to;
  private String subject;
  private String body;
}
