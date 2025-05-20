package com.example.product.mapper;

import com.example.product.dto.CreateProductRequestDto;
import com.example.product.models.Product;
import com.example.product.dto.UpdateProductRequestDto;
import com.example.product.dto.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toProduct(CreateProductRequestDto dto);
    Product toProduct(UpdateProductRequestDto dto);
    ProductResponseDto toDTO(Product product);
}