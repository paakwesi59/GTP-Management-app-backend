package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendInvitationEmail(String recipientName, String recipientEmail, String temporaryPassword, String passwordChangeLink) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariable("recipientName", recipientName);
        context.setVariable("temporaryPassword", temporaryPassword);
        context.setVariable("passwordChangeLink", passwordChangeLink);
        context.setVariable("recipientEmail", recipientEmail);
        String html = templateEngine.process("userInvitationEmail", context);

        message.setTo(recipientEmail);
        message.setSubject("Invitation to join the platform");
        message.setText(html, true);
        mailSender.send(mimeMessage);
    }

    public void sendResetTokenEmail(String recipientName, String recipientEmail, String resetToken) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

        String resetLink = "http://localhost:8080/api/user/reset-password?token=" + resetToken;
        logger.info("Reset link generated: {}", resetLink);

        Context context = new Context();
        context.setVariable("recipientName", recipientName);
        context.setVariable("resetLink", resetLink);
        context.setVariable("recipientEmail", recipientEmail);
        String html = templateEngine.process("forgotPasswordEmail", context);

        message.setTo(recipientEmail);
        message.setSubject("Password Reset Request");
        message.setText(html, true);
        mailSender.send(mimeMessage);
    }

    public void sendAccountConfirmationEmail(String recipientName, String recipientEmail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariable("recipientName", recipientName);
        context.setVariable("recipientEmail", recipientEmail);
        String html = templateEngine.process("confirmedAccountEmail", context);

        message.setTo(recipientEmail);
        message.setSubject("Account Confirmation");
        message.setText(html, true);
        mailSender.send(mimeMessage);
    }
}
