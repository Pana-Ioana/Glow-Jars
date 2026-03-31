package com.glowjar.backend.service;

import com.glowjar.backend.dto.InvoiceItemDto;
import com.glowjar.backend.dto.PreparedOrderDto;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class InvoiceService {

    public ByteArrayInputStream generateInvoice(PreparedOrderDto order) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            document.add(new Paragraph("Factura Glow Jar"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Numar comanda: " + order.getOrderNumber()));
            document.add(new Paragraph("Client: " + order.getFirstName() + " " + order.getLastName()));
            document.add(new Paragraph("Email: " + order.getEmail()));
            document.add(new Paragraph("Telefon: " + order.getPhone()));
            document.add(new Paragraph("Adresa: " + order.getAddress() + ", " + order.getCity() + ", " + order.getCounty()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Produse:"));
            document.add(new Paragraph(" "));

            if (order.getItems() != null) {
                for (InvoiceItemDto item : order.getItems()) {
                    document.add(new Paragraph(
                            item.getProductName()
                                    + " - " + item.getOptionName()
                                    + " | cantitate: " + item.getQuantity()
                                    + " | pret unitar: " + item.getUnitPrice() + " RON"
                                    + " | total: " + item.getLineTotal() + " RON"
                    ));
                }
            }

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total comanda: " + order.getTotal() + " RON"));

            document.close();

            byte[] pdfBytes = out.toByteArray();

            if (pdfBytes.length == 0) {
                throw new RuntimeException("PDF-ul generat este gol.");
            }

            return new ByteArrayInputStream(pdfBytes);

        } catch (Exception e) {
            throw new RuntimeException("Eroare la generarea facturii PDF.", e);
        }
    }
}