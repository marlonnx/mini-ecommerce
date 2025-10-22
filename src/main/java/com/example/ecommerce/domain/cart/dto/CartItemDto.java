package com.example.ecommerce.domain.cart.dto;

import com.example.ecommerce.domain.product.dto.ProductDto;

import java.io.Serializable;

/**
 * DTO for {@link com.example.ecommerce.domain.cart.model.CartItem}
 */
public record CartItemDto(Long id, ProductDto product, Integer quantity) implements Serializable {
}