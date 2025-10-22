package com.example.ecommerce.domain.order;

import com.example.ecommerce.domain.cart.dto.CartItemDto;
import com.example.ecommerce.domain.order.dto.OrderDto;
import com.example.ecommerce.domain.order.model.OrderItem;
import com.example.ecommerce.domain.order.model.OrderStatus;
import com.example.ecommerce.domain.product.ProductRepository;
import com.example.ecommerce.domain.user.User;
import com.example.ecommerce.exception.ApiException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper mapper;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ModelMapper mapper, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
        this.productRepository = productRepository;
    }

    public Page<OrderDto> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        final var orders = orderRepository.findAll(pageable);
        return orders.map(order -> mapper.map(order, OrderDto.class));
    }

    public Page<OrderDto> getUserOrders(long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        final var orders = orderRepository.findByUserId(userId, pageable);
        return orders.map(order -> mapper.map(order, OrderDto.class));
    }

    @Transactional
    public OrderDto saveOrder(List<CartItemDto> items, User user) {
        Order order = new Order();
        order.setUser(user);
        List<OrderItem> orderItems = new ArrayList<>();

        double total = 0;

        for (CartItemDto dto : items) {
            final var product = productRepository.findById(dto.product().id()).orElseThrow(() -> new ApiException("Product not found"));

            if (product.getQuantity() < dto.quantity()) {
                throw new ApiException("Product quantity too low: " + product.getName());
            }

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(dto.quantity());
            item.setOrder(order);
            orderItems.add(item);
            total += product.getPrice() * dto.quantity();
        }
        order.setItems(orderItems);
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.FINISHED);
        return mapper.map(orderRepository.save(order), OrderDto.class);
    }

    public OrderDto findById(long id) {
        return mapper.map(orderRepository.findById(id), OrderDto.class);
    }

    public OrderDto updateOrder(long id, OrderDto orderDto) {
        final var order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setTotalAmount(orderDto.totalAmount());
        order.setStatus(orderDto.status());
        order.setItems(orderDto.items().stream().map((element) -> mapper.map(element, OrderItem.class)).collect(Collectors.toList()));
        return mapper.map(orderRepository.save(order), OrderDto.class);
    }

    public void deleteById(long id) {
        if (orderRepository.findById(id).isPresent()) {
            orderRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Order not found");
        }
    }

}
