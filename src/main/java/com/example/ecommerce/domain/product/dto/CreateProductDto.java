package com.example.ecommerce.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProductDto(
        @NotBlank(message = "Name is required") String name,
        String description,
        @NotNull(message = "Quantity is required") Integer quantity,
        @NotNull Long categoryId
) {
}
