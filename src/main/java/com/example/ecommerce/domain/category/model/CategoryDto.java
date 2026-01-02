package com.example.ecommerce.domain.category.model;

import jakarta.validation.constraints.NotBlank;

public record CategoryDto(long id, @NotBlank String name, String image) {
}
