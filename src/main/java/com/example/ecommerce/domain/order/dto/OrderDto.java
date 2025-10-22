package com.example.ecommerce.domain.order.dto;

import com.example.ecommerce.domain.order.model.OrderItem;
import com.example.ecommerce.domain.order.model.OrderStatus;
import com.example.ecommerce.domain.product.dto.ProductDto;

import java.util.List;

public record OrderDto(
        Long id,
        List<OrderItem> items,
        Double totalAmount,
        OrderStatus status
) {
}
