package com.example.product.services;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.product.dto.ProductResponseDto;
import com.example.product.dto.UpdateProductRequestDto;
import com.example.product.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.product.dto.CreateProductRequestDto;
import com.example.product.dto.GetProductsRequestDto;
import com.example.product.exceptions.NotFoundException;
import com.example.product.models.Product;
import com.example.product.repositories.ProductRepository;
import jakarta.transaction.Transactional;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Map<String, Object> getProducts(GetProductsRequestDto requestDto) {
        logger.info("Get Producs request");
        Map<String, Object> res = new HashMap<>();

        Pageable pageRequest = PageRequest.of(
                requestDto.getPage(),
                requestDto.getSize(),
                Sort.by(parseSortString(requestDto.getSort()))
        );

        Page<Product> pageProds = productRepository.findAllByFilters(
                requestDto.getName(),
                requestDto.getPriceMin(),
                requestDto.getPriceMax(),
                requestDto.getCategory(),
                pageRequest);

        res.put("products", pageProds.getContent());
        res.put("currentPage", pageProds.getNumber());
        res.put("totalItems", pageProds.getTotalElements());
        res.put("totalPages", pageProds.getTotalPages());

        logger.info("Get Producs response");

        return res;
    }

    public ProductResponseDto getProduct(Long productId) {
        logger.info("Get Product by ID: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product does not exist with this ID: " + productId));
        return productMapper.toDTO(product);
    }

    public long createProduct(CreateProductRequestDto dto) {
        logger.info("Creating product request");
        var newProduct = productMapper.toProduct(dto);
        logger.info("Creating product response");
        return productRepository.save(newProduct).getId();
    }

    public ProductResponseDto updateProductById(Long productId, UpdateProductRequestDto dto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product does not exist with this ID: " + productId));

        var update = productMapper.toProduct(dto);

        product.setName(update.getName());
        product.setDescription(update.getDescription());
        product.setQuantity(update.getQuantity());
        product.setPrice(update.getPrice());
        product.setCategory(update.getCategory());

        Product updated = productRepository.save(product);

        return productMapper.toDTO(updated);
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product does not exist with this ID: " + productId));

        productRepository.deleteById(product.getId());
    }

    @Transactional
    public void initializeDatabaseIfEmpty() {
        if (productRepository.count() == 0) {
            List<Product> initialProducts = List.of(
                    Product.builder().name("Laptop").description("High-performance laptop").price(999.99f).category("Electronics").quantity(10).build(),
                    Product.builder().name("Smartphone").description("High-performance laptop").price(999.99f).category("Electronics").quantity(10).build(),
                    Product.builder().name("Chair").description("Ergonomic laptop").price(999.99f).category("Furniture").quantity(10).build(),
                    Product.builder().name("Desk").description("Ergonomic laptop").price(999.99f).category("Furniture").quantity(10).build(),
                    Product.builder().name("Suit").description("High-performance laptop").price(999.99f).category("Clothing").quantity(10).build()
            );

            productRepository.saveAll(initialProducts);
            System.out.println("Database initialized with default products.");
        } else {
            System.out.println("Database already contains products.");
        }
    }

    private Sort.Order[] parseSortString(String sort) {
        // Parse the sort string, e.g., "name,asc" into Sort.Order objects
        String[] parts = sort.split(",");
        if (parts.length == 2) {
            String property = parts[0];
            String direction = parts[1];
            return new Sort.Order[]{new Sort.Order(Sort.Direction.fromString(direction), property)};
        }
        return new Sort.Order[]{};
    }
}