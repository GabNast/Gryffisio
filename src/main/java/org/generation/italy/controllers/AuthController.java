package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.CreateUserRequest;
import org.generation.italy.model.dto.LoginRequest;
import org.generation.italy.model.dto.LoginResponse;
import org.generation.italy.model.dto.UserDto;
import org.generation.italy.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout() {
        // JWT authentication is stateless; clients log out by discarding the token.
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto createUser(@Valid @RequestBody CreateUserRequest request) {
        return authService.createUser(request);
    }
}
