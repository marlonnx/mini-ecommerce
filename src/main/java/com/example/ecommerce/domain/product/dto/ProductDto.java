package com.example.ecommerce.domain.product.dto;

import java.util.List;

public record ProductDto(
        Long id,
        String name,
        String description,
        Integer quantity,
        List<String> images,
        Double price
) {
}
