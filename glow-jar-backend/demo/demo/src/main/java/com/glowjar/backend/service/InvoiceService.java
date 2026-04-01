package com.glowjar.backend.service;

import com.glowjar.backend.entities.Order;
import com.glowjar.backend.entities.OrderItem;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class InvoiceService {

    public ByteArrayInputStream generateInvoicePdf(Order order) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();
            document.add(new Paragraph("Factura Glow Jar"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Numar comanda: " + order.getId()));
            document.add(new Paragraph("Client: " + order.getFullName()));
            document.add(new Paragraph("Email: " + order.getEmail()));
            document.add(new Paragraph("Telefon: " + order.getPhone()));
            document.add(new Paragraph("Adresa: " + order.getAddress()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Produse:"));

            if (order.getItems() != null) {
                for (OrderItem item : order.getItems()) {
                    document.add(new Paragraph(
                            "- " + item.getProductName()
                                    + " | Cantitate: " + item.getQuantity()
                                    + " | Pret: " + String.format("%.2f", item.getPrice()) + " RON"
                    ));

                    if (item.getCustomization() != null && !item.getCustomization().isBlank()) {
                        document.add(new Paragraph("  Personalizare: " + item.getCustomization()));
                    }
                }
            }

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total: " + String.format("%.2f", order.getTotal()) + " RON"));

            document.close();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            System.out.println("PDF GENERATION FAILED: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}