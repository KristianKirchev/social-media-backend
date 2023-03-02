package com.kristian.socmed.config;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfigurationProperties {
	@NotBlank
	private String url;

	@NotBlank
	private String frontUrl;
}
