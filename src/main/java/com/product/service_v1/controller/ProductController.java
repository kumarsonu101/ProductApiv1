package com.product.service_v1.controller;

import com.product.service_v1.entity.Product;
import com.product.service_v1.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@Validated
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        log.info("Creating product: {}", product);
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @GetMapping
    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        log.info("Fetching product by ID: {}", id);
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody Product updated) {
        log.info("Updating product ID {}: {}", id, updated);
        return productService.updateProduct(id, updated);
    }

    @PatchMapping("/{id}")
    public Product patchProduct(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        log.info("Patching product ID {} with fields: {}", id, updates.keySet());
        return productService.patchProduct(id, updates);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        log.info("Deleting product ID: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
