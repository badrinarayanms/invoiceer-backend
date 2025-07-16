package com.badri.invoice.repository;


import com.badri.invoice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Default CRUD methods like save, findAll, findById, deleteById
}
