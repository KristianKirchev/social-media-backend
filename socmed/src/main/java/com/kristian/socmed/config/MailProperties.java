package com.kristian.socmed.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "mail")
public class MailProperties {
  @NotBlank
  private String host;

  @Positive
  private Integer port;

  @NotBlank
  private String username;

  @NotBlank
  @ToString.Exclude
  private String password;

  @NotBlank
  private String transportProtocol = "smtp";

  @NotNull
  private Boolean smtpAuth = Boolean.TRUE;

  @NotNull
  private Boolean smtpStartTlsEnable = Boolean.TRUE;

  @NotNull
  private Boolean debug = Boolean.FALSE;

}
