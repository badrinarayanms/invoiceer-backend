package com.badri.invoice.controller;


import com.badri.invoice.model.Invoice;
import com.badri.invoice.model.InvoiceItem;
import com.badri.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class InvoiceController {
    @Autowired
    private InvoiceService invservice;

    @GetMapping("/invoices")
    public ResponseEntity<List<Invoice>> getAllInvoices(){

        return new ResponseEntity<>(invservice.getAllInvoices(), HttpStatus.OK);
    }

    @GetMapping("/invoices/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id){

        return new ResponseEntity<>(invservice.getInvoiceById(id), HttpStatus.OK);
    }

    @PostMapping("/invoice")
    public ResponseEntity<String> createInvoice(@RequestBody Invoice invoice) {
        for (InvoiceItem item : invoice.getItems()) {
            item.setId(null); // ✅ Reset to ensure it's treated as a new row
            item.setInvoice(invoice);
            item.setPriceAtTime(item.getProduct().getPrice());
        }

        try {
            invservice.createInvoice(invoice);
            return ResponseEntity.ok("Invoice created and emailed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create invoice: " + e.getMessage());
        }
    }

}
