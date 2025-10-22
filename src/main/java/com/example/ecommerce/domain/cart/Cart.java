package com.example.ecommerce.domain.cart;

import com.example.ecommerce.domain.cart.model.CartItem;
import com.example.ecommerce.domain.order.model.OrderItem;
import com.example.ecommerce.domain.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> items = new ArrayList<>();

    @Transient
    public Double getTotalAmount() {
        return items.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }
}