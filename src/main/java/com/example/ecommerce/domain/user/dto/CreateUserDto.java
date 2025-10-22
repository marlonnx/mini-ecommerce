package com.example.ecommerce.domain.user.dto;

import com.example.ecommerce.domain.user.model.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserDto(
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password is required")
        String password,
        @NotBlank(message = "Name is required")
        String name,
        @NotNull(message = "Role is required")
        UserRole role
) {
}
