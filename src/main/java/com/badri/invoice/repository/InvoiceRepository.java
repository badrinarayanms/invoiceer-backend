package com.badri.invoice.repository;


import com.badri.invoice.model.Invoice;
import com.badri.invoice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    // You can add custom query methods if needed later
    List<Invoice> findByOwner(User owner);
    Optional<Invoice> findByIdAndOwner(Long id, User owner);

}
