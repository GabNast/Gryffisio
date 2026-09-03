package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.CreateUserRequest;
import org.generation.italy.model.dto.OperatorDto;
import org.generation.italy.model.dto.OperatorRequest;
import org.generation.italy.model.dto.ResetPasswordRequest;
import org.generation.italy.services.OperatorService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operators")
public class OperatorController {
    private final OperatorService operatorService;

    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    @GetMapping("/{id}")
    public OperatorDto getById(@PathVariable Integer id) {
        return operatorService.findById(id);
    }

    @GetMapping
    public List<OperatorDto> getAll() {
        return operatorService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public OperatorDto create(@Valid @RequestBody CreateUserRequest request) {
        return operatorService.createOperator(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public OperatorDto update(@PathVariable Integer id, @Valid @RequestBody OperatorRequest request) {
        return operatorService.updateOperator(id, request);
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("hasRole('ADMIN')")
    public OperatorDto resetPassword(@PathVariable Integer id, @Valid @RequestBody ResetPasswordRequest request) {
        return operatorService.resetPassword(id, request.newPassword());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer id) {
        operatorService.deleteOperator(id);
    }
}