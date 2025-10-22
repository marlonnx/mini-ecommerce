package com.example.ecommerce.security.dto;

import com.example.ecommerce.domain.user.dto.UserDto;

public record AuthenticationResponse(
        String token,
        long expiresIn,
        UserDto user
) {
}
