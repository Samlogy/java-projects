package com.example.product.integration;

import com.example.product.dto.CreateProductRequestDto;
import com.example.product.dto.ProductResponseDto;
import com.example.product.dto.UpdateProductRequestDto;
import com.example.product.exceptions.NotFoundException;
import com.example.product.models.Product;
import com.example.product.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    private final String BASE_URL = "/api/products";

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void itShouldGetAllProducts() {
        // Given
        Product product1 = productRepository.save(Product.builder()
                .name("Laptop").description("Powerful laptop").price(1500.0).category("Electronics").quantity(10)
                .build());

        Product product2 = productRepository.save(Product.builder()
                .name("Phone").description("Latest smartphone").price(800.0).category("Electronics").quantity(10)
                .build());

        // When
        ResponseEntity<List<Product>> response = restTemplate.exchange(
                BASE_URL, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void itShouldReturn200ExistentProduct() {
        // Given
        Product savedProduct = productRepository.save(Product.builder()
                .name("Laptop").description("Powerful laptop").price(1500.0).category("Electronics").quantity(10)
                .build());

        // When
        ResponseEntity<ProductResponseDto> response = restTemplate.exchange(
                BASE_URL + "/" + savedProduct.getId(), HttpMethod.GET, null, ProductResponseDto.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void itShouldReturn404ForNonExistentProduct() {
        // When
        ResponseEntity<NotFoundException> response = restTemplate.exchange(
                BASE_URL + "/0", HttpMethod.GET, null, NotFoundException.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void itShouldCreateProductAndReturn201() {
        // Given
        CreateProductRequestDto product = CreateProductRequestDto.builder()
                .name("Laptop").description("Powerful laptop").price(1500.0).category("Electronics").quantity(10)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateProductRequestDto> request = new HttpEntity<>(product, headers);

        // When
        ResponseEntity<ProductResponseDto> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, request, ProductResponseDto.class);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void itShouldUpdateProductAndReturn200() {
        // Given
        Product savedProduct = productRepository.save(Product.builder()
                .name("Laptop").description("Powerful laptop").price(1500.0).category("Electronics").quantity(10)
                .build());

        UpdateProductRequestDto product = UpdateProductRequestDto.builder()
                .name("Updated Laptop").description("Even more powerful laptop").price(1700.0).category("Electronics").quantity(5)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UpdateProductRequestDto> request = new HttpEntity<>(product, headers);

        // When
        ResponseEntity<ProductResponseDto> response = restTemplate.exchange(
                BASE_URL + "/" + savedProduct.getId(), HttpMethod.PUT, request, ProductResponseDto.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void itShouldNotUpdateProductAndReturn404() {
        // Given
        UpdateProductRequestDto product = UpdateProductRequestDto.builder()
                .name("Laptop").description("Powerful laptop").price(1500.0).category("Electronics").quantity(10)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UpdateProductRequestDto> request = new HttpEntity<>(product, headers);

        // When
        ResponseEntity<ProductResponseDto> response = restTemplate.exchange(
                BASE_URL + "/0", HttpMethod.PUT, request, ProductResponseDto.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void itShouldDeleteProductAndReturn204() {
        // Given
        Product savedProduct = productRepository.save(Product.builder()
                .name("Laptop").description("Powerful laptop").price(1500.0).category("Electronics").quantity(10)
                .build());

        // When
        ResponseEntity<?> response = restTemplate.exchange(
                BASE_URL + "/" + savedProduct.getId(), HttpMethod.DELETE, null, null);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void itShouldNotDeleteProductAndReturn404() {
        // When
        ResponseEntity<?> response = restTemplate.exchange(
                BASE_URL + "/0", HttpMethod.DELETE, (HttpEntity<?>) null, (ParameterizedTypeReference<Object>) null);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
