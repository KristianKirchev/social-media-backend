package com.kristian.socmed.config;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfigurationProperties {
	@NotNull
    private String url;

    @NotNull
    @Value("${app.front.url}")
    private String frontUrl;
}
