package com.example.product;

import com.example.product.services.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final ProductService productService;

    public DatabaseInitializer(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run(String... args) {
        productService.initializeDatabaseIfEmpty();
    }
}
