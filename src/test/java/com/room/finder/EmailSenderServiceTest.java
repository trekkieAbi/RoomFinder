package com.room.finder;

import com.room.finder.constant.AppConstant;
import com.room.finder.model.Email;
import com.room.finder.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class EmailSenderServiceTest {
    @Autowired
    private EmailServiceImpl emailService;

    @Test
    public void sendHtmlMessageTest() throws MessagingException {
        Email email = new Email();
        email.setTo("lahoti.ashish20@gmail.com");
        email.setFrom("lahoti.ashish20@gmail.com");
        email.setSubject("Welcome Email from CodingNConcepts");
        email.setTemplate("welcome-email.html");
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", "Ashish");
        properties.put("subject", AppConstant.ACCEPTED_SUBJECT);
        properties.put("body",AppConstant.ACCEPTED_BODY);
        email.setProperties(properties);

        Assertions.assertDoesNotThrow(() -> emailService.sendHtmlMessage(email));
    }
}
