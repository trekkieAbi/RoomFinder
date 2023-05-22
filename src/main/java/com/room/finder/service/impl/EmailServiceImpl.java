package com.room.finder.service.impl;

import com.room.finder.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

@Service
public class EmailServiceImpl {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    private String host="smtp.gmail.com";
    private String port="25";
    private String username="trekkieabishek94@gmail.com";
    private String password="zmlxjmztjwpiqewy";

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

   /* public void sendSimpleMessage(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email.getFrom());
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getText());

       // log.info("Sending email: {} with text body: {}", email, email.getText());
        javaMailSender.send(message);
    }*/


    public String sendSimpleMail(Email details) {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getTo());
            mailMessage.setText(details.getText());
            mailMessage.setSubject(details.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
    }

}
