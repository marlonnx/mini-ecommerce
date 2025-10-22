package com.example.ecommerce.domain.order;

import com.example.ecommerce.domain.cart.dto.CartItemDto;
import com.example.ecommerce.domain.order.dto.OrderDto;
import com.example.ecommerce.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {
    final private OrderService orderService;

    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "/me")
    @PreAuthorize("hasRole('COSTUMER')")
    public Page<OrderDto> findMyOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal User user
    ) {
        return orderService.getUserOrders(user.getId(), page, size);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public Page<OrderDto> findAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return orderService.getAllOrders(page, size);
    }


    @PostMapping()
    public OrderDto saveOrder(@RequestBody List<CartItemDto> items, @AuthenticationPrincipal User user) {
        return orderService.saveOrder(items, user);
    }


}
