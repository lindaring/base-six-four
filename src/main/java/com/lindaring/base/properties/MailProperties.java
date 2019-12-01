package com.lindaring.base.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "api.mail")
public class MailProperties {
  private final Hit hit = new Hit();
  private final Register register = new Register();
  private final Approval approval = new Approval();

  @Data
  public static class Hit {
    private boolean enabled;
    private String to;
    private String subject;
    private String body;
  }

  @Data
  public static class Register {
    private boolean enabled;
    private String link;
    private String subject;
    private String body;
  }

  @Data
  public static class Approval {
    private boolean enabled;
    private String to;
    private String link;
    private String subject;
    private String body;
  }
}
