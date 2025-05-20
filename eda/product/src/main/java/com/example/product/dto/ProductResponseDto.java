package com.example.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@JsonInclude(JsonInclude.Include.NON_NULL) // ✅ Ignore les champs nulls
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private int quantity;
    private float price;
    private String category;
}