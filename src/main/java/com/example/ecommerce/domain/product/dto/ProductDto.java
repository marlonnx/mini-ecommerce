package com.example.ecommerce.domain.product.dto;

import com.example.ecommerce.domain.category.model.CategoryDto;

import java.util.List;

public record ProductDto(
        Long id,
        String name,
        String description,
        Integer quantity,
        List<String> images,
        Double price,
        CategoryDto category
) {
}
