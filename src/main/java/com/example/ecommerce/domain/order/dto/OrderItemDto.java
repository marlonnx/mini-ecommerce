package com.example.ecommerce.domain.order.dto;

import com.example.ecommerce.domain.product.Product;

public record OrderItemDto(
        Long id,
        Integer quantity,
        Product product
) {
}
