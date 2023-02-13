package com.kristian.socmed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.kristian.socmed.exception.MyRuntimeException;
import com.kristian.socmed.model.entity.VerificationEmail;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailService {
	@Autowired
    @Qualifier("gmail")
	private final JavaMailSender mailSender;

    @Async
    public void sendMail(VerificationEmail verificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("smilenetix@gmail.com");
            messageHelper.setTo(verificationEmail.getReceiver());
            messageHelper.setSubject(verificationEmail.getSubject());
            messageHelper.setText(verificationEmail.getContent());
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            throw new MyRuntimeException("Exception occurred when sending mail to " + verificationEmail.getReceiver() + " " + e.getMessage());
        }
    }
}
