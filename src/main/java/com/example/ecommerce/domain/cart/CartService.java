package com.example.ecommerce.domain.cart;

import com.example.ecommerce.domain.cart.dto.CartDto;
import com.example.ecommerce.domain.user.User;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    final private CartRepository cartRepository;
    final private ModelMapper modelMapper;

    public CartService(CartRepository cartRepository, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
    }

    public List<CartDto> findAll() {
        List<Cart> carts = cartRepository.findAll();
        return carts.stream()
                .map((element) -> modelMapper.map(element, CartDto.class))
                .toList();
    }

    public CartDto findUserCart(Long userId) {
        final var cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        return modelMapper.map(cart, CartDto.class);
    }

    public CartDto findById(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found with id " + id));
        return modelMapper.map(cart, CartDto.class);
    }

    public  CartDto save(CartDto cartDto, User user) {
        Cart cart = modelMapper.map(cartDto, Cart.class);
        cart.setUser(user);
        return modelMapper.map(cartRepository.save(cart), CartDto.class);
    }

    public void  delete(Long id) {
        cartRepository.deleteById(id);
    }

}
