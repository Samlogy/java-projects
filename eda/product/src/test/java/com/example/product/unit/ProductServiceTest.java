package com.example.product.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.product.dto.*;
import com.example.product.exceptions.NotFoundException;
import com.example.product.mapper.ProductMapper;
import com.example.product.models.Product;
import com.example.product.repositories.ProductRepository;
import com.example.product.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product mockProduct;
    private ProductResponseDto mockProductDto;

    @BeforeEach
    void setUp() {
        mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setName("Test Product");
        mockProduct.setDescription("Test Description");
        mockProduct.setPrice(100.0f);
        mockProduct.setQuantity(10);
        mockProduct.setCategory("Electronics");

        mockProductDto = new ProductResponseDto();
        mockProductDto.setId(1L);
        mockProductDto.setName("Test Product");
        mockProductDto.setDescription("Test Description");
        mockProductDto.setPrice(100.0f);
        mockProductDto.setQuantity(10);
        mockProductDto.setCategory("Electronics");
    }

    @Test
    void testGetProducts_Success() {
        GetProductsRequestDto requestDto = new GetProductsRequestDto();
        requestDto.setPage(0);
        requestDto.setSize(5);
        requestDto.setSort("name,asc");

        List<Product> products = List.of(mockProduct);
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "name"));
        Page<Product> page = new PageImpl<>(products, pageable, products.size());

        when(productRepository.findAllByFilters(any(), any(), any(), any(), any())).thenReturn(page);

        Map<String, Object> result = productService.getProducts(requestDto);

        assertNotNull(result);
        assertEquals(1, ((List<?>) result.get("products")).size());
        assertEquals(0, result.get("currentPage"));
        assertEquals(1L, result.get("totalItems"));
        assertEquals(1, result.get("totalPages"));
    }

    @Test
    void testGetProductById_Found() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        when(productMapper.toDTO(mockProduct)).thenReturn(mockProductDto);

        ProductResponseDto result = productService.getProduct(1L);

        assertNotNull(result);
        assertEquals(mockProductDto.getId(), result.getId());
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.getProduct(1L));
    }

    @Test
    void testCreateProduct_Success() {
        CreateProductRequestDto createDto = new CreateProductRequestDto();
        when(productMapper.toProduct(createDto)).thenReturn(mockProduct);
        when(productRepository.save(mockProduct)).thenReturn(mockProduct);

        long productId = productService.createProduct(createDto);

        assertEquals(1L, productId);
    }

    @Test
    void testUpdateProductById_Success() {
        UpdateProductRequestDto updateDto = new UpdateProductRequestDto();
        updateDto.setName("Updated Name");

        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        when(productMapper.toProduct(updateDto)).thenReturn(mockProduct);
        when(productRepository.save(mockProduct)).thenReturn(mockProduct);
        when(productMapper.toDTO(mockProduct)).thenReturn(mockProductDto);

        ProductResponseDto result = productService.updateProductById(1L, updateDto);

        assertNotNull(result);
        assertEquals("Test Product", result.getName()); // Ensure product name is unchanged from mock setup
    }

    @Test
    void testUpdateProductById_NotFound() {
        UpdateProductRequestDto updateDto = new UpdateProductRequestDto();
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.updateProductById(1L, updateDto));
    }

    @Test
    void testDeleteProduct_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

        assertDoesNotThrow(() -> productService.deleteProduct(1L));

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.deleteProduct(1L));
    }

}
