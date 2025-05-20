package com.example.product.dto;

import lombok.*;


@Getter
@Setter
public class GetProductsRequestDto {
    private int page = 0;
    private int size = 10;
    private String sort = "price,asc";
    private String name;
    private float priceMin;
    private float priceMax;
    private String category;
}
