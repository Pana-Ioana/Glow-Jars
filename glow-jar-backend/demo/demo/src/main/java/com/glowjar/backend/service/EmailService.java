package com.glowjar.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

import java.io.ByteArrayInputStream;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationCode(String toEmail, String firstName, String code, String subject, String introText) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(
                "Buna, " + firstName + "!\n\n" +
                        introText + "\n\n" +
                        "Codul tau este: " + code + "\n\n" +
                        "Codul expira in 10 minute.\n\n" +
                        "Daca nu tu ai facut aceasta cerere, ignora acest email."
        );

        mailSender.send(message);
    }

    public void sendOrderConfirmation(String toEmail, String firstName, String orderNumber, Double total) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Confirmare comanda Glow Jar");
        message.setText(
                "Buna, " + firstName + "!\n\n" +
                        "Comanda ta a fost plasata cu succes.\n\n" +
                        "Numar comanda: " + orderNumber + "\n" +
                        "Total: " + total + " RON\n\n" +
                        "Vei primi factura in atasament intr-un email separat.\n\n" +
                        "Glow Jar"
        );

        mailSender.send(message);
    }

    public void sendInvoiceEmail(String toEmail, String firstName, String fileName, ByteArrayInputStream pdfStream) {
        try {
            byte[] pdfBytes = pdfStream.readAllBytes();

            if (pdfBytes.length == 0) {
                throw new RuntimeException("Atasamentul PDF este gol.");
            }

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Factura Glow Jar");
            helper.setText(
                    "Buna, " + firstName + "!\n\n" +
                            "Iti atasam factura comenzii tale.\n\n" +
                            "Glow Jar"
            );

            helper.addAttachment(
                    fileName,
                    new ByteArrayResource(pdfBytes),
                    "application/pdf"
            );

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Eroare la trimiterea facturii.", e);
        }
    }
}