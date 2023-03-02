package com.kristian.socmed.config;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
