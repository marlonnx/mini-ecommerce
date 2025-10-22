package com.example.ecommerce.domain.user.dto;

import com.example.ecommerce.domain.user.model.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class UserDto {
    private  Long id;
    private  String email;
    private  String name;
    private  UserRole role;

}
