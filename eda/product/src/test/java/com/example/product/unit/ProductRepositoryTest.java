package com.example.product.unit;

import com.example.product.controllers.ProductController;
import com.example.product.dto.ProductResponseDto;
import com.example.product.exceptions.NotFoundException;
import com.example.product.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductResponseDto mockProductDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        mockProductDto = new ProductResponseDto();
        mockProductDto.setId(1L);
        mockProductDto.setName("Test Product");
        mockProductDto.setDescription("Test Description");
        mockProductDto.setPrice(100.0f);
        mockProductDto.setQuantity(10);
        mockProductDto.setCategory("Electronics");
    }

    @Test
    void testGetProducts_Success() throws Exception {
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("products", List.of(mockProductDto));
        mockResponse.put("currentPage", 0);
        mockResponse.put("totalItems", 1);
        mockResponse.put("totalPages", 1);

        when(productService.getProducts(any())).thenReturn(mockResponse);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.currentPage").value(0));
    }

    @Test
    void testGetProducts_Empty() throws Exception {
        when(productService.getProducts(any())).thenReturn(Collections.emptyMap());

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    void testGetProductById_Found() throws Exception {
        when(productService.getProduct(1L)).thenReturn(mockProductDto);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        when(productService.getProduct(1L)).thenThrow(new NotFoundException("Product not found"));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateProduct_Success() throws Exception {
        when(productService.createProduct(any())).thenReturn(1L);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Product\",\"description\":\"Description\",\"price\":99.99,\"quantity\":5,\"category\":\"Tech\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateProduct_BadRequest() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"description\":\"\",\"price\":-1,\"quantity\":-5,\"category\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateProduct_Success() throws Exception {
        when(productService.updateProductById(eq(1L), any())).thenReturn(mockProductDto);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Name\",\"description\":\"Updated Desc\",\"price\":120.50,\"quantity\":15,\"category\":\"Updated Tech\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateProduct_NotFound() throws Exception {
        when(productService.updateProductById(eq(1L), any())).thenThrow(new NotFoundException("Product not found"));

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Name\",\"description\":\"Updated Desc\",\"price\":120.50,\"quantity\":15,\"category\":\"Updated Tech\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProduct_Success() throws Exception {
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteProduct_NotFound() throws Exception {
        doThrow(new NotFoundException("Product not found")).when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNotFound());
    }
}

