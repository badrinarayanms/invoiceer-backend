package com.badri.invoice.service;

import com.badri.invoice.model.*;
import com.badri.invoice.repository.InvoiceItemRepository;
import com.badri.invoice.repository.InvoiceRepository;
import com.badri.invoice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invRepo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private InvoiceItemRepository invItemRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    AuthUserService authUserService;
    public List<Invoice> getAllInvoices() {
        User user = authUserService.getCurrentUser();
        return invRepo.findByOwner(user);

    }

    public Invoice getInvoiceById(Long id) {
//        return invRepo.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
        User user = authUserService.getCurrentUser();
        return invRepo.findByIdAndOwner(id, user)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    public void createInvoice(Invoice invoice) throws Exception {
//        invoice.setInvoiceDate(LocalDateTime.now());
//
//        double totalAmount = 0;
//
//        for (InvoiceItem item : invoice.getItems()) {
//            // Fetch the complete product from DB
//            Long productId = item.getProduct().getId();
//            Product fullProduct = productRepo.findById(productId)
//                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
//
//            item.setProduct(fullProduct);
//            item.setInvoice(invoice);
//            item.setPriceAtTime(fullProduct.getPrice());
//
//            totalAmount += item.getQuantity() * item.getPriceAtTime();
//        }
//
//        invoice.setTotalAmount(totalAmount);
//
//        Invoice savedInvoice = invRepo.save(invoice);
//
//        byte[] pdfBytes = pdfGenerator.generateInvoicePdf(savedInvoice);
//        emailService.sendInvoice(invoice.getCustomerEmail(), pdfBytes);

        User user = authUserService.getCurrentUser();
        invoice.setOwner(user);
        invoice.setInvoiceDate(LocalDateTime.now());

        double totalAmount = 0;

        for (InvoiceItem item : invoice.getItems()) {

            Long productId = item.getProduct().getId();

            // 🔐 Product must belong to same user
            Product product = productRepo
                    .findByIdAndOwner(productId, user)
                    .orElseThrow(() ->
                            new RuntimeException("Unauthorized product access"));

            item.setProduct(product);
            item.setInvoice(invoice);
            item.setPriceAtTime(product.getPrice());

            totalAmount += item.getQuantity() * item.getPriceAtTime();
        }

        invoice.setTotalAmount(totalAmount);

        Invoice savedInvoice = invRepo.save(invoice);

        byte[] pdfBytes = pdfGenerator.generateInvoicePdf(savedInvoice);
        emailService.sendInvoice(savedInvoice.getCustomerEmail(), pdfBytes);
    }


}
