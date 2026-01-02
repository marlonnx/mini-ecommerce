package com.example.ecommerce.domain.category;

import com.example.ecommerce.domain.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String image;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true,fetch =  FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

}