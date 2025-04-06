package com.product.service_v1.service;


import com.product.service_v1.entity.Product;
import com.product.service_v1.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);


    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) {
        logger.info("Saving new product: {}", product.getName());
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        logger.info("Fetching all products");
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        logger.info("Fetching product with ID: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", id);
                    return new EntityNotFoundException("Product not found with id: " + id);
                });
    }

    public void deleteProduct(Long id) {
        logger.info("Deleting product with ID: {}", id);
        if (!productRepository.existsById(id)) {
            logger.warn("Attempted to delete non-existent product ID: {}", id);
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }


    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = getProductById(id);
        existing.setName(updatedProduct.getName());
        existing.setQuantity(updatedProduct.getQuantity());
        existing.setPrice(updatedProduct.getPrice());
        existing.setDescription(updatedProduct.getDescription());
        return productRepository.save(existing);
    }

    public Product patchProduct(Long id, Map<String, Object> updates) {
        Product product = getProductById(id);
        updates.forEach((field, value) -> {
            switch (field) {
                case "name": product.setName((String) value); break;
                case "quantity": product.setQuantity((Integer) value); break;
                case "price": product.setPrice(new java.math.BigDecimal(value.toString())); break;
                case "description": product.setDescription((String) value); break;
            }
        });
        return productRepository.save(product);
    }
}
