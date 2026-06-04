package com.badri.invoice.model;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Component
public class PdfGenerator {

    public byte[] generateInvoicePdf(Invoice invoice) {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.setMargins(30, 30, 30, 30);

            /* ---------- HEADER ---------- */

            Paragraph billType = new Paragraph("BILL OF SUPPLY   |   ORIGINAL FOR RECIPIENT | ITS NOT A REAL INVOICE")
                    .setFontSize(10)
                            .simulateBold();

            document.add(billType);

            Paragraph companyName = new Paragraph(
                    invoice.getOwner().getCompanyName()
            )
                    .setFontSize(20)
                    .simulateBold()
                    .setFontColor(com.itextpdf.kernel.colors.ColorConstants.RED);

            document.add(companyName);

            document.add(new LineSeparator(new SolidLine()));

            /* ---------- INVOICE META ---------- */

            Table metaTable = new Table(new float[]{1, 1});
            metaTable.setWidth(UnitValue.createPercentValue(100));

            metaTable.addCell(new Cell().add(
                    new Paragraph("Invoice No: " + invoice.getId())
            ).setBorder(Border.NO_BORDER));

            metaTable.addCell(new Cell().add(
                            new Paragraph("Invoice Date: " +
                                    invoice.getInvoiceDate()
                                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    ).setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER));

            document.add(metaTable);

            document.add(new Paragraph("\n"));

            /* ---------- CUSTOMER ---------- */

            document.add(new Paragraph("Bill To:")
                    .simulateBold());

            document.add(new Paragraph(invoice.getCustomerName()));

            document.add(new Paragraph("\n"));

            /* ---------- ITEMS TABLE ---------- */

            Table table = new Table(new float[]{4, 2, 2, 2});
            table.setWidth(UnitValue.createPercentValue(100));

            table.addHeaderCell(headerCell("ITEMS"));
            table.addHeaderCell(headerCell("QTY"));
            table.addHeaderCell(headerCell("RATE"));
            table.addHeaderCell(headerCell("AMOUNT"));

            for (InvoiceItem item : invoice.getItems()) {
                table.addCell(normalCell(item.getProduct().getName()));
                table.addCell(normalCell(String.valueOf(item.getQuantity())));
                table.addCell(normalCell("₹" + item.getPriceAtTime()));
                table.addCell(normalCell("₹" + item.getQuantity() * item.getPriceAtTime()));
            }

            document.add(table);

            document.add(new Paragraph("\n"));

            /* ---------- TOTAL ---------- */

            Table totalTable = new Table(new float[]{6, 2});
            totalTable.setWidth(UnitValue.createPercentValue(100));

            totalTable.addCell(new Cell()
                    .add(new Paragraph("Subtotal"))
                    .setBorder(Border.NO_BORDER));

            totalTable.addCell(new Cell()
                    .add(new Paragraph("₹" + invoice.getTotalAmount()))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER));

            totalTable.addCell(new Cell()
                    .add(new Paragraph("Total Amount").simulateBold())
                    .setBorder(Border.NO_BORDER));

            totalTable.addCell(new Cell()
                    .add(new Paragraph("₹" + invoice.getTotalAmount()).simulateBold())
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER));

            document.add(totalTable);

            document.add(new Paragraph("\n"));

            /* ---------- AMOUNT IN WORDS ---------- */

            document.add(new Paragraph(
                    "Total Amount (in words): "
                            + NumberToWords.convert(invoice.getTotalAmount()) + " Rupees"
            ).simulateItalic());

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate invoice PDF", e);
        }
    }

    /* ---------- HELPER METHODS ---------- */

    private Cell headerCell(String text) {
        return new Cell()
                .add(new Paragraph(text).simulateBold())
                .setTextAlignment(TextAlignment.CENTER);
    }

    private Cell normalCell(String text) {
        return new Cell()
                .add(new Paragraph(text))
                .setTextAlignment(TextAlignment.CENTER);
    }
}
