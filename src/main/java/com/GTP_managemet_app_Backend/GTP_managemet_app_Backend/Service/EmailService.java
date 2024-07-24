package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendInvitationEmail(String email, String username) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true, "Utf8");
        message.setTo(email);
        message.setSubject("Invitation to join the platform");
        message.setText( "Your temporary password: "+ username );
        String content = "<p>Click the link below to login:</p>"
                + "<p><a href=\"http://localhost:8080/api/user/login" +"\">login</a></p>"
                +"<p>\" Your temporary password is: "+ username+ "</p>";
        message.setText(content, true);
        mailSender.send(mimeMessage);
    }

    public void sendResetTokenEmail(String email, String token) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setTo(email);
        message.setSubject("Password Reset Request");
        String content = "<p>Click the link below to reset your password:</p>"
                + "<p><a href=\"http://localhost:8080/api/user/reset-password?token=" + token + "\">Reset Password</a></p>";
        message.setText(content, true);
        mailSender.send(mimeMessage);
    }
}
