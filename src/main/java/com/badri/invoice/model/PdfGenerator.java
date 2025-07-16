    package com.badri.invoice.model;



    import com.badri.invoice.model.Invoice;
    import com.badri.invoice.model.InvoiceItem;
    import com.itextpdf.kernel.pdf.PdfWriter;
    import com.itextpdf.kernel.pdf.PdfDocument;
    import com.itextpdf.layout.Document;
    import com.itextpdf.layout.element.*;

    import org.springframework.stereotype.Component;

    import java.io.ByteArrayOutputStream;
    import com.itextpdf.layout.properties.TextAlignment;
    import com.itextpdf.layout.properties.UnitValue;

    @Component
    public class PdfGenerator {

        public byte[] generateInvoicePdf(Invoice invoice) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                PdfWriter writer = new PdfWriter(baos);
                PdfDocument pdfDoc = new PdfDocument(writer);
                Document document = new Document(pdfDoc);


                document.add(new Paragraph("INVOICE")
                        .setFontSize(20)// This still gives an error in iText 7. Use the solution below.
                        .setTextAlignment(TextAlignment.CENTER));

                document.add(new Paragraph("Customer: " +
                        (invoice.getCustomerName() != null ? invoice.getCustomerName() : "N/A")));

                document.add(new Paragraph("Email: " +
                        (invoice.getCustomerEmail() != null ? invoice.getCustomerEmail() : "N/A")));

                document.add(new Paragraph("Date: " +
                        (invoice.getInvoiceDate() != null ? invoice.getInvoiceDate().toString() : "N/A")));


                document.add(new Paragraph(" ")); // line break

                Table table = new Table(new float[]{4, 2, 2, 2});
                table.setWidth(UnitValue.createPercentValue(100));
                table.addHeaderCell("Product");
                table.addHeaderCell("Price");
                table.addHeaderCell("Qty");
                table.addHeaderCell("Total");

                for (InvoiceItem item : invoice.getItems()) {
                    String productName = (item.getProduct() != null && item.getProduct().getName() != null)
                            ? item.getProduct().getName() : "Unnamed Product";

                    table.addCell(productName);

                    table.addCell(String.valueOf(item.getPriceAtTime()));
                    table.addCell(String.valueOf(item.getQuantity()));
                    double total = item.getQuantity() * item.getPriceAtTime();
                    table.addCell("₹" + String.format("%.2f", total));

                }

                document.add(table);
                document.add(new Paragraph("Total Amount: ₹" + invoice.getTotalAmount()));

                document.close();
                System.out.println("Generating PDF for customer: " + invoice.getCustomerName());

                return baos.toByteArray();
            } catch (Exception e) {
                throw new RuntimeException("Failed to generate PDF: " + e.getMessage());
            }

        }
    }
