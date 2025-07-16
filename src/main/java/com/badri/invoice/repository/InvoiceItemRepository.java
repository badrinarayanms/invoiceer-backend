package com.badri.invoice.repository;

import com.badri.invoice.model.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem,Long> {
    boolean existsByProductId(Long productId);
}
