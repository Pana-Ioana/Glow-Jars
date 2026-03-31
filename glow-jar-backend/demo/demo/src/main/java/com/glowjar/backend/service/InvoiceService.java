package com.glowjar.backend.service;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.stripe.model.climate.Order;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class InvoiceService {

    public ByteArrayInputStream generateInvoice(Order order) {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("Factura Glow Jar"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Client: " + order.getUser().getFirstName()));
            document.add(new Paragraph("Email: " + order.getUser().getEmail()));
            document.add(new Paragraph("Total: " + order.getTotal() + " RON"));

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}