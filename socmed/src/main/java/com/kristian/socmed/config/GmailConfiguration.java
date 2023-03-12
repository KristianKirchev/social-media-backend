package com.kristian.socmed.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class GmailConfiguration {

  @Bean("gmail")
  JavaMailSender gmailMailSender(MailProperties mailProperties) {
    log.info("Configuring gmail sender, mailProperties:{}", mailProperties);

    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(mailProperties.getHost());
    mailSender.setPort(mailProperties.getPort());

    mailSender.setUsername(mailProperties.getUsername());
    mailSender.setPassword(mailProperties.getPassword());

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", mailProperties.getTransportProtocol());
    props.put("mail.smtp.auth", mailProperties.getSmtpAuth().toString());
    props.put("mail.smtp.starttls.enable", mailProperties.getSmtpStartTlsEnable().toString());
    props.put("mail.debug", mailProperties.getDebug().toString());

    return mailSender;
  }
}
