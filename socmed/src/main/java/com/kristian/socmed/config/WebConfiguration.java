package com.kristian.socmed.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebMvc
@AllArgsConstructor
@Slf4j
public class WebConfiguration implements WebMvcConfigurer {
  private AppProperties appProperties;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    log.debug("Adding Cors Mappings, appProperties:{}", appProperties);
    
    registry.addMapping("/**")
        .allowedOriginPatterns(appProperties.getFrontUrl())
        .allowedMethods("*")
        .maxAge(3600L)
        .allowedHeaders("*")
        .exposedHeaders("Authorization")
        .allowCredentials(true);
  }
  
//  @Override
//  public void configurePathMatch(PathMatchConfigurer configurer) {
//    configurer.setUseTrailingSlashMatch(true);
//  }
}
