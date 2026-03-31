package com.glowjar.backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String to, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Cod verificare cont Glow Jar");
            helper.setText("""
                    Bună,

                    Codul tău de verificare este: %s

                    Introdu acest cod în pagină pentru a-ți activa contul.

                    Glow Jar
                    """.formatted(code));

            mailSender.send(message);
            System.out.println("MAIL SENT TO: " + to);

        } catch (MailException | MessagingException e) {
            System.out.println("MAIL FAILED: " + e.getMessage());
        }
    }
}