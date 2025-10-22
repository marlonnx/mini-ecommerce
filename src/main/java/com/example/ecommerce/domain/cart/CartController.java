package com.example.ecommerce.domain.cart;

import com.example.ecommerce.domain.cart.dto.CartDto;
import com.example.ecommerce.domain.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/carts")
public class CartController {
    final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<CartDto> findAll() {
        return cartService.findAll();
    }

    @GetMapping(path = "/me")
    public CartDto findByUser(@AuthenticationPrincipal User user) {
        return cartService.findUserCart(user.getId());
    }

    @PostMapping
    public CartDto save(@RequestBody CartDto cartDto, @AuthenticationPrincipal User user) {
        return cartService.save(cartDto, user);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        cartService.delete(id);
    }
}
