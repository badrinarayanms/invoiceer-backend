package com.badri.invoice.repository;


import com.badri.invoice.model.Product;
import com.badri.invoice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Default CRUD methods like save, findAll, findById, deleteById
    List<Product> findByOwnerAndActiveTrue(User owner);
    Optional<Product> findByIdAndOwner(Long id, User owner);
}
