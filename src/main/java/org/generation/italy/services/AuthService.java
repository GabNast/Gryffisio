package org.generation.italy.services;

import org.generation.italy.model.dto.LoginRequest;
import org.generation.italy.model.dto.LoginResponse;
import org.generation.italy.model.entities.Operator;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.model.repositories.OperatorRepository;
import org.generation.italy.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final OperatorRepository operatorRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            OperatorRepository operatorRepository,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.operatorRepository = operatorRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) throws NotFoundException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        Operator operator = operatorRepository.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new NotFoundException("operator_not_found", "Operator not found: " + request.email()));

        String token = jwtService.createToken(operator);
        return new LoginResponse(token);
    }
}