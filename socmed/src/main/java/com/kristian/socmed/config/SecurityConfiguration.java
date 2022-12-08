package com.kristian.socmed.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import lombok.AllArgsConstructor;

@EnableWebSecurity
@AllArgsConstructor
@Configuration
public class SecurityConfiguration {
	private RsaKeyProperties rsaKeyProperties;
	
}
