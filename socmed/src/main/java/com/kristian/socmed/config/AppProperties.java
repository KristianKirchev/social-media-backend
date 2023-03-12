package com.kristian.socmed.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
  @NotBlank
  private String url;

  @NotBlank
  private String frontUrl;
}
