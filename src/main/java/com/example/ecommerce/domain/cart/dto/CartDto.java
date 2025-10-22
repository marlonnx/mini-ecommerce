package com.example.ecommerce.domain.cart.dto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.example.ecommerce.domain.cart.Cart}
 */
public record CartDto(Long id, List<CartItemDto> items) implements Serializable {
}