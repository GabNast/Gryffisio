package org.generation.italy.services;

import org.generation.italy.model.dto.CreateUserRequest;
import org.generation.italy.model.dto.LoginRequest;
import org.generation.italy.model.dto.LoginResponse;
import org.generation.italy.model.dto.OperatorDto;
import org.generation.italy.model.entities.Operator;
import org.generation.italy.model.exceptions.ConflictException;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.model.repositories.OperatorRepository;
import org.generation.italy.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final OperatorRepository operatorRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            OperatorRepository operatorRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.operatorRepository = operatorRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        Operator operator = operatorRepository.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new NotFoundException("operator_not_found", "Operator not found: " + request.email()));

        String token = jwtService.createToken(operator);
        return new LoginResponse(token);
    }

    @Transactional
    public OperatorDto createUser(CreateUserRequest request) {
        if (operatorRepository.existsByEmailIgnoreCase(request.email())) {
            throw new ConflictException("email_unavailable", "Email already exists: " + request.email());
        }

        Operator operator = new Operator();
        operator.setFirstName(request.firstName());
        operator.setLastName(request.lastName());
        operator.setEmail(request.email());
        operator.setRole(Operator.Role.ADMIN);
        operator.setPasswordHash(passwordEncoder.encode(request.password()));

        Operator saved = operatorRepository.save(operator);
        return new OperatorDto(saved.getId(), saved.getFirstName(), saved.getLastName(), saved.getEmail(), saved.getRole().name());
    }
}