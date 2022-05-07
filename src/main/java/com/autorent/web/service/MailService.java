package com.autorent.web.service;


import com.autorent.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailSender mailSender;

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;



    @Async
    public void sendHtmlEmail(String to, String subject, User user,
                              String link, String templateName,
                              Locale locale) throws MessagingException {
        final Context ctx = new Context(locale);
        ctx.setVariable("name", user.getName());
        ctx.setVariable("url", link);

        final String htmlContent = templateEngine.process(templateName, ctx);


        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper message =
                new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setSubject(subject);
        message.setTo(to);

        message.setText(htmlContent, true);


        this.javaMailSender.send(mimeMessage);
    }

    @Async
    public void sendMail(String toEmail, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        mailSender.send(simpleMailMessage);
    }






}
