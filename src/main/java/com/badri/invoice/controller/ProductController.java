package com.badri.invoice.controller;

import com.badri.invoice.model.Product;
import com.badri.invoice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        product.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(product));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product product = productService.updateProduct(id, updatedProduct);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            return productService.deleteProduct(id)
                    ? ResponseEntity.ok("Product deleted")
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}
