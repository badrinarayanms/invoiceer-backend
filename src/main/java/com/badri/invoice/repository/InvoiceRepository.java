package com.badri.invoice.repository;


import com.badri.invoice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    // You can add custom query methods if needed later
}
