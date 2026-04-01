package com.glowjar.backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationCode(String to,
                                     String firstName,
                                     String verificationCode,
                                     String subject,
                                     String appName) {
        String safeFirstName = firstName != null ? firstName : "";
        String safeCode = verificationCode != null ? verificationCode : "";
        String safeSubject = subject != null && !subject.isBlank()
                ? subject
                : "Cod verificare cont";
        String safeAppName = appName != null && !appName.isBlank()
                ? appName
                : "Glow Jar";

        String text = """
                Buna %s,

                Codul tau de verificare este: %s

                Introdu acest cod in aplicatie pentru a continua.

                %s
                """.formatted(safeFirstName, safeCode, safeAppName);

        sendSimpleEmail(to, safeSubject, text);
    }

    public void sendOrderConfirmation(String to,
                                      String firstName,
                                      String orderNumber,
                                      Double totalAmount) {
        String safeFirstName = firstName != null ? firstName : "";
        String safeOrderNumber = orderNumber != null ? orderNumber : "-";
        String safeAmount = totalAmount != null ? String.format("%.2f", totalAmount) : "0.00";

        String subject = "Confirmare comanda Glow Jar";
        String text = """
                Buna %s,

                Comanda ta a fost inregistrata cu succes.

                Numar comanda: %s
                Total: %s RON

                Iti multumim ca ai ales Glow Jar.
                """.formatted(safeFirstName, safeOrderNumber, safeAmount);

        sendSimpleEmail(to, subject, text);
    }

    public void sendInvoiceEmail(String to,
                                 String firstName,
                                 String orderNumber,
                                 ByteArrayInputStream pdfStream) {
        String safeFirstName = firstName != null ? firstName : "";
        String safeOrderNumber = orderNumber != null ? orderNumber : "-";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("Factura Glow Jar - comanda " + safeOrderNumber);
            helper.setText("""
                    Buna %s,

                    Atasat gasesti factura pentru comanda ta %s.

                    Glow Jar
                    """.formatted(safeFirstName, safeOrderNumber));

            if (pdfStream != null) {
                byte[] pdfBytes = pdfStream.readAllBytes();

                helper.addAttachment(
                        "factura-" + safeOrderNumber + ".pdf",
                        new InputStreamSource() {
                            @Override
                            public ByteArrayInputStream getInputStream() {
                                return new ByteArrayInputStream(pdfBytes);
                            }
                        }
                );
            }

            mailSender.send(message);
            System.out.println("EMAIL SENT TO: " + to);

        } catch (MailException | MessagingException e) {
            System.out.println("INVOICE EMAIL FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendSimpleEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            mailSender.send(message);
            System.out.println("EMAIL SENT TO: " + to);

        } catch (MailException | MessagingException e) {
            System.out.println("EMAIL FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }
}