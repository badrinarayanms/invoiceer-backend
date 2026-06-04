package com.badri.invoice.service;

import com.resend.Resend;
import com.resend.services.emails.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class EmailService {

    @Autowired
    private Resend resend;

    public void sendInvoice(String toEmail, byte[] pdfBytes) {

        try {

            String base64Pdf =
                    Base64.getEncoder().encodeToString(pdfBytes);

            Attachment attachment = Attachment.builder()
                    .fileName("invoice.pdf")
                    .content(base64Pdf)
                    .build();

            CreateEmailOptions params =
                    CreateEmailOptions.builder()
                            .from("onboarding@resend.dev")
                            .to(toEmail)
                            .subject("Your Invoice")
                            .html(
                                    "<h2>Your Invoice</h2>" +
                                            "<p>Please find your invoice attached.</p>"
                            )
                            .attachments(attachment)
                            .build();

            resend.emails().send(params);

            System.out.println("Invoice email sent");

        } catch (Exception e) {
            System.out.println(
                    "Email failed: " + e.getMessage()
            );
            throw new RuntimeException(e);
        }
    }
}