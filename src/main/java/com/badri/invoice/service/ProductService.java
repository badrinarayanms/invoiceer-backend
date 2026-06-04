package com.badri.invoice.service;

import com.badri.invoice.model.Product;
import com.badri.invoice.model.User;
import com.badri.invoice.repository.InvoiceItemRepository;
import com.badri.invoice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @Autowired
    private AuthUserService authUserService;

    public List<Product> getAllProducts() {
        User user = authUserService.getCurrentUser();
        return productRepo.findByOwnerAndActiveTrue(user);

    }

    public Product getProductById(Long id) {
        User user = authUserService.getCurrentUser();
        return productRepo.findByIdAndOwner(id, user)
                .orElseThrow(() -> new RuntimeException("Product not found"));
//        return productRepo.findById(id).orElse(null);
    }

    public Product addProduct(Product product) {
        product.setOwner(authUserService.getCurrentUser());
        product.setActive(true);
        return productRepo.save(product);

    }

    public Product getMyProductById(Long id) {
        User user = authUserService.getCurrentUser();

        return productRepo.findByIdAndOwner(id, user)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }


    public Product updateProduct(Long id, Product updatedProduct) {


        Product product = getMyProductById(id);
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        return productRepo.save(product);


//
//        return productRepo.findById(id).map(product -> {
//            product.setName(updatedProduct.getName());
//            product.setPrice(updatedProduct.getPrice());
//            return productRepo.save(product);
//        }).orElse(null);
    }

//    public boolean deleteProduct(Long id) {
//        if (!productRepo.existsById(id)) return false;
//
//        // Check if product is used in any invoice
//        boolean isProductUsed = invoiceItemRepository.existsByProductId(id);
//        if (isProductUsed) {
//            throw new IllegalStateException("Product is linked to invoices and cannot be deleted");
//        }
//
//        productRepo.deleteById(id);
//        return true;
//    }

    public void disableProduct(Long id) {
        Product product = getMyProductById(id);
        product.setActive(false);
        productRepo.save(product);
    }
}
