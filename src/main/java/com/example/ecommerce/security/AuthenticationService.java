package com.example.ecommerce.security;

import com.example.ecommerce.domain.user.User;
import com.example.ecommerce.domain.user.UserRepository;
import com.example.ecommerce.domain.user.dto.CreateUserDto;
import com.example.ecommerce.domain.user.dto.UserDto;
import com.example.ecommerce.security.dto.AuthenticationRequest;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final ModelMapper mapper;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            ModelMapper mapper
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    public UserDto signup(CreateUserDto input) {
        User user = User.builder()
                .name(input.name())
                .email(input.email())
                .password(passwordEncoder.encode(input.password()))
                .role(input.role())
                .build();
        return mapper.map(userRepository.save(user), UserDto.class);
    }

    public User authenticate(AuthenticationRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.email(),
                        input.password()
                )
        );

        return userRepository.findByEmail(input.email())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}