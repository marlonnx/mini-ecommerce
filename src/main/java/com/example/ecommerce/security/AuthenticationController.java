package com.example.ecommerce.security;

import com.example.ecommerce.domain.user.User;
import com.example.ecommerce.domain.user.dto.CreateUserDto;
import com.example.ecommerce.domain.user.dto.UserDto;
import com.example.ecommerce.security.dto.AuthenticationRequest;
import com.example.ecommerce.security.dto.AuthenticationResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authService;
    private final ModelMapper mapper;

    AuthenticationController(JwtService jwtService, AuthenticationService authService, ModelMapper mapper) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.mapper = mapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> register(@Valid @RequestBody CreateUserDto createUserDto) {
        var registeredUser = this.authService.signup(createUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest loginUserDto) {
        User authenticatedUser = authService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        return ResponseEntity.ok(
                new AuthenticationResponse(
                        jwtToken,
                        jwtService.getExpirationTime(),
                        mapper.map(authenticatedUser, UserDto.class)
                )
        );
    }
}
