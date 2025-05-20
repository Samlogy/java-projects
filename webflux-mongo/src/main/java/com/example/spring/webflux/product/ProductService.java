package com.example.spring.webflux.product;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    // Create Product
    public Mono<Product> createProduct(Product product) {
        return productRepository.save(product)
                .doOnSuccess(savedProduct -> logger.info("Product created: {}", savedProduct))
                .doOnError(error -> logger.error("Error saving product: {}", error.getMessage()));
    }

    // Get All Products
    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get Product by ID
    public Mono<Product> getProductById(Integer id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found!")));
    }

    // Update Product
    public Mono<Product> updateProduct(Integer id, Product updatedProduct) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found!")))
                .flatMap(existingProduct -> {
                    existingProduct.setName(updatedProduct.getName());
                    existingProduct.setPrice(updatedProduct.getPrice());
                    return productRepository.save(existingProduct);
                })
                .doOnSuccess(prod -> logger.info("Product updated: {}", prod));
    }

    // Delete Product
    public Mono<Void> deleteProduct(Integer id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found!")))
                .flatMap(productRepository::delete)
                .doOnSuccess(v -> logger.info("Product {} deleted successfully", id));
    }
}
