package com.kristian.socmed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.kristian.socmed.config.AppProperties;
import com.kristian.socmed.config.RsaKeyProperties;

@SpringBootApplication
@EnableConfigurationProperties({AppProperties.class, RsaKeyProperties.class})
public class SocmedApplication {
  public static void main(String[] args) {
    SpringApplication.run(SocmedApplication.class, args);
  }
}
