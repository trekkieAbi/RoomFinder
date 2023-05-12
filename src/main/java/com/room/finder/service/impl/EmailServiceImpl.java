package com.room.finder.service.impl;

import com.room.finder.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    public void sendHtmlMessage(Email email) throws MessagingException {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage,MimeMessageHelper.MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name());
        Context context=new Context();
        context.setVariables(email.getProperties());
        messageHelper.setFrom(email.getFrom());
        messageHelper.setTo(email.getTo());
        messageHelper.setSubject(email.getSubject());
        String html=springTemplateEngine.process(email.getTemplate(),context);
        messageHelper.setText(html,true);
        javaMailSender.send(mimeMessage);
    }

}
