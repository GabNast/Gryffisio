package org.generation.italy.services;

import org.generation.italy.model.dto.CreateUserRequest;
import org.generation.italy.model.dto.LoginRequest;
import org.generation.italy.model.dto.LoginResponse;
import org.generation.italy.model.dto.UserDto;
import org.generation.italy.model.entities.Admin;
import org.generation.italy.model.exceptions.BadRequestException;
import org.generation.italy.model.exceptions.ConflictException;
import org.generation.italy.model.repositories.AppUserRepository;
import org.generation.italy.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.generation.italy.model.exceptions.NotFoundException;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.name(), request.password())
        );

        Admin user = appUserRepository.findByName(request.name())
                .orElseThrow(() -> new NotFoundException("user_not_found", "User not found: " + request.name()));

        String token = jwtService.createToken(user);
        return new LoginResponse(token);
    }

    @Transactional
    public UserDto createUser(CreateUserRequest request) {
        if (request.name() == null || request.name().isBlank()) {
            throw new BadRequestException("invalid_request", "Username is required");
        }
        if (request.password() == null || request.password().isBlank()) {
            throw new BadRequestException("invalid_request", "Password is required");
        }
        if (appUserRepository.existsByName(request.name())) {
            throw new ConflictException("username_unavailable", "Username already exists: " + request.name());
        }


        Admin user = new Admin();
        user.setName(request.name());
        user.setPasswordHash(passwordEncoder.encode(request.password()));

        Admin saved = appUserRepository.save(user);
        return new UserDto(
                saved.getId(),
                saved.getName()
        );
    }
}
