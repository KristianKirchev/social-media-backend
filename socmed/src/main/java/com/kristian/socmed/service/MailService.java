package com.kristian.socmed.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.kristian.socmed.model.entity.VerificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
  @Qualifier("gmail")
  private final JavaMailSender mailSender;

  @Async
  public void sendMail(VerificationEmail verificationEmail) {
    log.debug("Sending email, verificationEmail:{}");

    MimeMessagePreparator messagePreparator = mimeMessage -> {
      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
      messageHelper.setFrom("smilenetix@gmail.com");
      messageHelper.setTo(verificationEmail.getReceiver());
      messageHelper.setSubject(verificationEmail.getSubject());
      messageHelper.setText(verificationEmail.getContent());
    };

    try {
      mailSender.send(messagePreparator);
    } catch (Exception e) {
      log.error("Could not send mail, verificationMail:{}", verificationEmail, e);
    }
  }
}
